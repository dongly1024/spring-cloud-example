package top.keir.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import top.keir.order.dubbo.DubboConsumer;

@Slf4j
@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    DubboConsumer dubboConsumer;

    @Test
    void contextLoads() {
        log.info("discoveryClient: {}", discoveryClient.getServices());
    }

}
