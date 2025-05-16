package top.keir.gateway.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * 响应结果
 */
@Data
@AllArgsConstructor
public class Result {

    private final Integer status;
    /**
     * 响应消息: 这里应该根据不同status值返回对应的消息模板消息
     */
    private final String message;

    public static Result ok() {
        return new Result(200, "ok");
    }

    public static Result error(String msg) {
        return new Result(500, msg);
    }

    public boolean isOk() {
        return Objects.equals(200, this.status);
    }

    public boolean isError() {
        return !isOk();
    }

}
