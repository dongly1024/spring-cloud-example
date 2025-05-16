package top.keir.gateway.strategy;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.codec.binary.Base62;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 白名单：主要是为了防止大量恶意请求影响正常用户访问
 * <p>
 * 具体实现：
 * 1.只允许存在于白名单的用户或者IP访问，但需开启白名单
 * 2.白名单的数据主要来自三部分：数据库加载到redis，手动添加到redis
 * 3.白名单是基于方法级别限制，只有匹配才会触发白名单功能
 *
 * @author Keir
 */
@Slf4j
@Component
@AllArgsConstructor
public class WhitelistSecurityStrategy implements SecurityStrategy<JSONObject> {

    private final RedissonClient redissonClient;

    @Override
    public Result doSecurity(Request<JSONObject> request) {
        // 1. 获取URI判断是否在白名单中
        String uri = request.uri();
        if (!isWhitelistEnabled(uri)) {
            return Result.ok();
        }
        // 2. 判断用户唯一标识或IP是否在白名单中，存在白名单直接放行
        String ip = request.ip();
        String userId = request.userId();
        if (isIpInWhitelist(ip) || isUserIdInWhitelist(userId)) {
            return Result.ok();
        }
        return Result.error("系统升级，稍后再试");
    }

    /**
     * 判断URI是否在白名单
     *
     * @param uri
     * @return
     */
    private boolean isWhitelistEnabled(String uri) {
        String key = "gateway:s:w:uri";
        return redissonClient.getSet(key).contains(Base62.encode(uri));
    }

    /**
     * 判断UserId是否在白名单
     *
     * @param userId
     * @return
     */
    private boolean isUserIdInWhitelist(String userId) {
        String key = "gateway:s:w:userId";
        return redissonClient.getSet(key).contains(userId);
    }

    /**
     * 判断IP是否在白名单
     *
     * @param ip
     * @return
     */
    private boolean isIpInWhitelist(String ip) {
        String key = "gateway:s:w:ip";
        return redissonClient.getSet(key).contains(Base62.encode(ip));
    }
}
