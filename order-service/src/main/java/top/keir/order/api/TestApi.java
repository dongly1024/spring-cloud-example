package top.keir.order.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.keir.order.config.OrderProperties;
import top.keir.order.dubbo.DubboConsumer;
import top.keir.order.feign.ProductApi;
import top.keir.service.product.Product;

import java.util.List;

/**
 * @param productApi
 * @param test
 * @RefreshScope 自动刷新
 */
@Slf4j
@RestController
public record TestApi(ProductApi productApi, OrderProperties properties, DubboConsumer consumer) {

    @GetMapping(value = "/feign/all")
    public ResponseEntity<List<Product>> feignAll() {
        return productApi.all();
    }

    @GetMapping(value = "/feign/getByCode")
    public ResponseEntity<Product> feignGetByCode(@RequestParam(value = "code") String code) {
        return productApi.getByCode(code);
    }

    @GetMapping(value = "/dubbo/all")
    public ResponseEntity<List<Product>> all() {
        return ResponseEntity.ok(consumer.productService.all());
    }

    @GetMapping(value = "/dubbo/getByCode")
    public ResponseEntity<Product> getByCode(@RequestParam(value = "code") String code) {
        return ResponseEntity.ok(consumer.productService.getByCode(code));
    }


}
