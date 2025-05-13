package top.keir.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.keir.service.product.Product;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductApi {


    /**
     * 获取所有商品
     *
     * @return 商品列表
     */
    @GetMapping(value = "/all")
    ResponseEntity<List<Product>> all();

    /**
     * 根据code获取商品
     *
     * @param code 商品code
     * @return 商品
     */
    @GetMapping(value = "/getByCode")
    ResponseEntity<Product> getByCode(@RequestParam("code") String code);

}
