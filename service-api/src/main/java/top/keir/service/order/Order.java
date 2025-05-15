package top.keir.service.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

    private String orderId;
    private String productCode;
    private String productName;

}
