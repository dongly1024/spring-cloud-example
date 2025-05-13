package top.keir.order.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.keir.order.config.OrderProperties;
import top.keir.order.dubbo.DubboConsumer;
import top.keir.order.feign.ProductApi;

import java.util.List;

/**
 * @param productApi
 * @param test
 * @RefreshScope 自动刷新
 */
@Slf4j
@RestController
public record TestApi(ProductApi productApi, OrderProperties properties, DubboConsumer consumer) {

    @GetMapping(value = "/all")
    public ResponseEntity<List<String>> all() {
        log.info("consumer:{}", consumer.testService.sayHello("keir"));
        log.info("test：{}", properties.getTest());
        ResponseEntity<List<String>> all = productApi.all();
        log.info("order-service: {}", all.getBody());
        return all;
    }

    @GetMapping(value = "/say")
    public ResponseEntity<List<String>> say(@RequestParam(value = "name") String name) {
        log.info("say name:{}", name);
        String say1 = consumer.testService.sayHello(name);
        String sa2 = consumer.test2Service.sayHello(name);
        List<String> list = List.of(say1, sa2);
        log.info("say name: {}", list);
        return ResponseEntity.ok(list);
    }


}
