package top.keir.gateway;

import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@SpringBootTest
class GatewayServiceApplicationTests {

    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    void contextLoads() {
        discoveryClient.getServices().forEach(System.out::println);
    }

}
