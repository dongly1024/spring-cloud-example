package top.keir.order.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keir.order.config.OrderProperties;
import top.keir.order.feign.ProductApi;

import java.util.List;

/**
 * @param productApi
 * @param test
 * @RefreshScope 自动刷新
 */
@Slf4j
@RestController
public record TestApi(ProductApi productApi, OrderProperties properties) {

    @GetMapping(value = "/all")
    public ResponseEntity<List<String>> all() {
        log.info("test：{}", properties.getTest());
        ResponseEntity<List<String>> all = productApi.all();
        log.info("order-service: {}", all.getBody());
        return all;
    }


}
