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
@ConfigurationProperties(prefix = "common")
public class CommonProperties implements Serializable {

    private String testUserId;
    private String appKey;
    private String secretKey;
    @Value("${desc}")
    private String desc;
}
