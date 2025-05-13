## 介绍
本项目主要是整合nacos、gateway、feign、dubbo
## 环境
java: 17
springboot: 3.4.5
nacos: 2.5.1
### 配置中心配置
#### order服务
文件名：order-service.yaml
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.13:8848
        username: nacos
        password: 123456
        namespace: a993f4ef-e2af-4b78-9364-5c19bf945bab
server:
  port: 9010
order:
  test: 这是测试内容111
```