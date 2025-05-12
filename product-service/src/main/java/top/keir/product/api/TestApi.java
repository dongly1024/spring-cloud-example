package top.keir.product.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TestApi {

    @GetMapping(value = "/all")
    public ResponseEntity<List<String>> all() {
        log.info("获取商品列表");
        // 商品列表
        List<String> list = List.of("商品1", "商品2", "商品3", "商品4", "商品5");
        return ResponseEntity.ok(list);
    }
}
