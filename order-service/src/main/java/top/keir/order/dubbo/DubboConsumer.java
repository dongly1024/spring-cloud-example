package top.keir.order.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import top.keir.service.product.ProductService;

@Component
public class DubboConsumer {

    @DubboReference
    public ProductService productService;

}
