package top.keir.product.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ProductServiceImpl implements ProductService  {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
