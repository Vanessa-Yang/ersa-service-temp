# ersa-service

## 目录结构

分布式项目：
  圈重点：spring security oauth2 + jwt（token） 实现分布式单点登录sso
  
  1. registry-server 注册中心 eureka
  2. sso Oauth2认证中心, jwt生成token
  3. member 会员系统，oauth-client1,认证客户端1
  4. order 订单系统, oauth-client2, 认证客户端2 
  
  待完善......
  
  更新：
  1. sso服务（即认证中心）中，添加微信扫码登录功能，实现方式基于jwt token + redis，并未与当前oauth2单点登录系统集成；
  2. oauth2 sso下集成第三方微信授权登录方式有待研究......
