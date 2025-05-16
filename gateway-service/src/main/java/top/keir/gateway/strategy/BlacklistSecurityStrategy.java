package top.keir.gateway.strategy;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.codec.binary.Base62;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 黑名单：用户或IP处罚某种限制规则
 * <p>
 * 具体实现：
 * 1.禁止存在于黑名单的用户或者IP访问
 * 2.黑名单的数据主要来自三部分：数据库加载到redis，手动添加到redis，系统自动触发（如超过系统调用接口限制阈值、多次获取验证码等）
 * 3.黑名单是基于方法级别限制，分为用户唯一标识和IP两种，主要其中有一个，就直接禁止，返回对应错误码及信息
 * 4.黑名单返回的错误信息模板需要定制，返回给前端
 *
 * @author Keir
 */
@Slf4j
@Component
@AllArgsConstructor
public class BlacklistSecurityStrategy implements SecurityStrategy<JSONObject> {

    private final RedissonClient redissonClient;

    @Override
    public Result doSecurity(Request<JSONObject> request) {
        // 1. 获取用户唯一标识和IP
        String userId = request.userId();
        String ip = request.ip();
        String uri = request.uri();
        Result result;
        if ((result = containsIp(uri, ip)).isError() || (result = containsUserId(uri, userId)).isError()) {
            // TODO 根据错误状态获取不同的模板消息返回
            return result;
        }
        return Result.ok();
    }

    /**
     * 判断用户唯一标识是否在黑名单中
     *
     * @param uri
     * @param userId
     * @return
     */
    private Result containsUserId(String uri, String userId) {
        String key = "gateway:s:b:u:%s".formatted(Base62.encode(uri));
        RMap<String, Result> map = redissonClient.getMap(key);
        return map.getOrDefault(userId, Result.ok());
    }

    /**
     * 判断IP是否在黑名单中
     *
     * @param uri
     * @param ip
     * @return
     */
    private Result containsIp(String uri, String ip) {
        String key = "gateway:s:b:i:%s".formatted(Base62.encode(uri));
        RMap<String, Result> map = redissonClient.getMap(key);
        return map.getOrDefault(ip, Result.ok());
    }
}
