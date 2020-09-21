# Config-Server
配置中心

默认使用Git仓库(本地git或者远程git均可)

说明： 
* 本地git以file://开头
* 远程git以实际地址为准，如https://github.com/caohonghua/spring-cloud-template-config-repo
* 远程git仓库为public，无需username/password，否则需要认证


### 刷新节点配置
*两点要求*
* 配置客户端（即应用服务器需要添加@RefreshScope注解）
* 依赖Spring-Cloud-Bus和消息中间件，rabbitmq/kafka
### 刷新语句 POST请求
http://config-server/actuator/bus-refresh
