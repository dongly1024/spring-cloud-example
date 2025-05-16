package top.keir.gateway.strategy;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.codec.hash.HashUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 表单重复提交
 * <p>
 * 具体实现：
 * 1.用户打开页面时，需请求获取requestId,这个是根据用户ID作为密钥，当前时间戳，返回的base64字符串
 * 2.判断是否指定的set中，存在则返回请勿重复提交请求，否则返回正常
 * 3.set要根据流量进行扩容，避免内存消耗过大
 *
 * @author Keir
 */
@Slf4j
@Component
@AllArgsConstructor
public class FormRepeatSubmitSecurityStrategy implements SecurityStrategy<JSONObject> {

    private final RedissonClient redissonClient;

    @Override
    public Result doSecurity(Request<JSONObject> request) {
        String userId = request.userId();
        String sign = request.sign();
        long secondOfDay = request.secondOfDay();
        if (!addSignInTable(userId, sign, secondOfDay)) {
            return Result.error("请勿重复提交请求");
        }

        return Result.ok();
    }


    /**
     * 添加sign值
     *
     * @param userId 用户ID
     * @param sign   签名
     * @return true表示添加成功，false添加失败
     */
    private boolean addSignInTable(String userId, String sign, long epochSecond) {
        // 每分钟存储一个值，key为当前分钟，value为桶的数量
        // 根据当前分钟数获取对应的桶的数量
        String tableBucketKey = "gateway:s:w:formRepeatSubmit:table";
        RMap<Integer, Integer> tableBucket = redissonClient.getMap(tableBucketKey);
        // 带有年月日的时间类型
        // 存储key依然为分钟，value则为具体年月日的天数 * 100 + 桶的数量
        LocalDateTime requestTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.systemDefault());
        // 当前小时的分钟，比如12:30,那么就等于30
        int bucketName = requestTime.getMinute();
        // 用此值取模获取桶的数量
        int divisor = 100;
        // 最小分区
        int minPartitionNum = 2;
        // 最大分区
        int maxPartitionNum = 99;
        int partitionNum = Optional.ofNullable(tableBucket.get(bucketName)).orElseGet(() -> {
            int epochDay = (int) requestTime.toLocalDate().toEpochDay();
            // 初始化分区个数
            int pm = epochDay * divisor + minPartitionNum;
            return Optional.ofNullable(tableBucket.putIfAbsent(bucketName, pm)).orElse(pm);
        });
        // 计算并初始化下一分钟的桶数量
        Integer bucketNextNum;
        int bucketNextName = (bucketName + 1) % 60;
        if ((bucketNextNum = tableBucket.getOrDefault(bucketNextName, 0)) > 0) {
            // 如果比当前时间小，则该桶已失效
            long day = LocalDate.now().toEpochDay();
            if (day < bucketNextNum / divisor) {
                int counter = 0;
                for (int i = 1; i <= 5; i++) {
                    counter += tableBucket.getOrDefault(Math.floorMod(bucketNextName % divisor - i, 60), 0);
                }
                int num = counter / 6000 + 1;
                tableBucket.fastPutIfAbsent(bucketNextName, day * divisor + num > maxPartitionNum ? maxPartitionNum : num);
            }
        }

        String userSign = "%s:%s".formatted(userId, sign);
        int index = HashUtil.bkdrHash(userSign) % partitionNum;
        String tableKey = "gateway:s:w:formRepeatSubmit:table:%s".formatted(index);
        Set<String> bucket = redissonClient.getSet(tableKey);
        if (!bucket.add(userSign)) {
            return false;
        }
        return !isSignInTable(userSign, tableBucket, requestTime, 5);
    }

    public Integer getBucketNextNum(RMap<Integer, Integer> tableBucket, int bucketName) {
        return tableBucket.get(bucketName);
    }

    /**
     * 判断sign是否存在于表中
     * <p>
     * 递归一次，则向前追寻一分钟，总计分钟数等于cycleNum
     *
     * @param tableBucket 表格桶
     * @param userSign    签名
     * @param cycleNum    递归次数
     * @return true表示存在，false表示不存在
     */
    private boolean isSignInTable(String userSign, RMap<Integer, Integer> tableBucket,
                                  LocalDateTime requestTime, int cycleNum) {

        int bucketName = requestTime.getMinute();
        Integer bucketNum = tableBucket.get(bucketName);
        if (Objects.nonNull(bucketNum)) {
            int index = HashUtil.bkdrHash(userSign) % (bucketNum / 100);
            String tableKey = "gateway:s:w:formRepeatSubmit:table:%s".formatted(index);
            Set<String> bucket = redissonClient.getSet(tableKey);
            if (bucket.contains(userSign)) {
                return true;
            }
        }
        if (--cycleNum <= 0) {
            return false;
        }
        return isSignInTable(userSign, tableBucket, requestTime.minusMinutes(1L), cycleNum);
    }


}
