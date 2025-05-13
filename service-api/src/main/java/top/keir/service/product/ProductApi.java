package top.keir.service.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ProductApi {

    @GetMapping(value = "/all")
    ResponseEntity<List<Product>> all();

    @GetMapping(value = "/getByCode")
    ResponseEntity<Product> getByCode();

}
