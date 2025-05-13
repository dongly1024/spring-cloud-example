package top.keir.product.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;
import top.keir.product.db.ProductDb;
import top.keir.service.product.Product;
import top.keir.service.product.ProductService;

import java.util.List;

@DubboService
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> all() {
        return ProductDb.PRODUCTS;
    }

    @Override
    public Product getByCode(String code) {
        return ProductDb.getByCode(code);
    }
}
