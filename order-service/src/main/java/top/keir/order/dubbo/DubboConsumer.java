package top.keir.order.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import top.keir.product.service.TestService;

@Component
public class DubboConsumer {

    @DubboReference
    public TestService testService;

    @DubboReference
    public Test2Service test2Service;

}
