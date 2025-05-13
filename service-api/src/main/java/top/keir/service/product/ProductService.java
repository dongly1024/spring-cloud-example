package top.keir.service.product;

import java.util.List;

/**
 * dubbo提供的服务
 */
public interface ProductService {

    /**
     * 获取所有商品
     *
     * @return 商品列表
     */
    List<Product> all();

    /**
     * 根据code获取商品
     *
     * @param code 商品code
     * @return 商品
     */
    Product getByCode(String code);

}
