package top.keir.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@Slf4j
@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    void contextLoads() {
        log.info("discoveryClient: {}", discoveryClient.getServices());
    }

}
