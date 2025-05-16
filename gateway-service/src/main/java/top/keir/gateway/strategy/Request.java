package top.keir.gateway.strategy;

/**
 * 客户端传入参数的副本
 *
 * @param ip        客户端请求的真实IP
 * @param userId    客户端请求的用户ID
 * @param uri       请求的uri
 * @param secondOfDay 当前天的时间戳
 * @param body      请求体。传给客户端最终的样子
 */
public record Request<T>(String ip, String userId, String uri, long secondOfDay, String sign, T body) {
}