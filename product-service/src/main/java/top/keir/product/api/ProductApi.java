package top.keir.product.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keir.product.db.ProductDb;
import top.keir.service.product.Product;

import java.util.List;

@Slf4j
@RestController
public class ProductApi {
    @GetMapping(value = "/all")
    public ResponseEntity<List<Product>> all() {
        log.info("获取商品列表");
        return ResponseEntity.ok(ProductDb.PRODUCTS);
    }

    @GetMapping(value = "/getByCode")
    public ResponseEntity<Product> getByCode(String code) {
        return ResponseEntity.ok(ProductDb.getByCode(code));
    }

}
