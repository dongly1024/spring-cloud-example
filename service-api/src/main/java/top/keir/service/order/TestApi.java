package top.keir.service.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface TestApi {

    @GetMapping(value = "/all")
    ResponseEntity<List<String>> all();

}
