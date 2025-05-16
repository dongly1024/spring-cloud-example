package top.keir.gateway.strategy;

public interface SecurityStrategy<T> {

    Result doSecurity(Request<T> request);

}
