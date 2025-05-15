package top.keir.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisProperties implements Serializable {

    private String host;
    private String port;
    private String password;
    @Value("${desc}")
    private String desc;

}
