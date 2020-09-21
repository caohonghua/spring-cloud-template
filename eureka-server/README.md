# Eureka-Server
注册中心

## 单节点配置
### application.yml
```
  server:
    port: 9000
  
  eureka:
    instance:
      hostname: localhost
    client:
      register-with-eureka: false
      fetch-registry: false
      service-url:
        defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

## 多节点配置
### 节点A application.yml
```
spring:
  application:
    name: eureka-server

eureka:
  instance:
    hostname: ip-nodeA
  client:
    service-url:
      defaultZone: http://ip-nodeB/eureka/
```

### 节点B application.yml
```
spring:
  application:
    name: eureka-server

eureka:
  instance:
    hostname: ip-nodeB
  client:
    service-url:
      defaultZone: http://ip-nodeA/eureka/
```