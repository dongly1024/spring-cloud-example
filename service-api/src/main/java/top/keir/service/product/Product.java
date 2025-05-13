package top.keir.service.product;

/**
 * 商品信息
 *
 * @param productCode 商品编号
 * @param productName 商品名称
 * @param productDesc 商品描述
 */
public record Product(String productCode, String productName, String productDesc) {
}
