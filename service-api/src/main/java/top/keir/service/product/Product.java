package top.keir.service.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品信息
 */
@Data
@AllArgsConstructor
public class Product implements Serializable {

    private String productCode;
    private String productName;
    private String productDesc;

}
