package top.keir.service.product;

import java.util.List;

public interface ProductService {

    List<Product> all();

    Product getByCode(String code);

}
