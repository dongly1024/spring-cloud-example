package top.keir.service.order;

/**
 * 订单
 *
 * @param orderId     订单ID
 * @param productCode 商品编码
 * @param productName 商品名称
 */
public record Order(String orderId, String productCode, String productName) {
}
