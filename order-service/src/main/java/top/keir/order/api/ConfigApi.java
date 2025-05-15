package top.keir.order.api;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keir.order.config.CommonProperties;
import top.keir.order.config.MysqlProperties;
import top.keir.order.config.OrderProperties;
import top.keir.order.config.RedisProperties;

import java.util.Map;

@RestController
public record ConfigApi(OrderProperties orderProperties,
                        MysqlProperties mysqlProperties,
                        RedisProperties redisProperties,
                        CommonProperties commonProperties) {

    @GetMapping(value = "config")
    public String config() {
        return JSONObject.of(
                "order", orderProperties,
                "mysql", mysqlProperties,
                "common", commonProperties
        ).toJSONString();
    }
}
