# ersa-service

## 目录结构

分布式项目：
  圈重点：spring security oauth2 + jwt（token） 实现分布式单点登录sso
  
  1. registry-server 注册中心 eureka
  2. sso Oauth2认证中心, jwt生成token
  3. member 会员系统，oauth-client1,认证客户端1
  4. order 订单系统, oauth-client2, 认证客户端2 
  
  待完善......
  
