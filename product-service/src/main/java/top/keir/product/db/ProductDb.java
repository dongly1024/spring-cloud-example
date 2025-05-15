package top.keir.product.db;

import top.keir.service.product.Product;

import java.util.List;

public interface ProductDb {

    List<Product> PRODUCTS = List.of(
            new Product("P00", "辅助注册", "辅助注册"),
            new Product("P01", "商标注册", "商标智能注册"),
            new Product("P02", "商标续展", "商标续展"),
            new Product("P03", "版权服务", "版权服务")
    );

    static Product getByCode(String code) {
        return PRODUCTS.stream().filter(product -> product.getProductCode().equals(code)).findFirst().orElse(null);
    }

}
