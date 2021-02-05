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
 
 备注：
 
    1. 认证中心所依赖的mysql表位于ersa-service-temp/sso/resources/..
    
    2. 微信扫码登录依赖的app_id,app_secret以及redirect_id来源于网上技术大牛在demo中提供并作映射配置后的地址，
    
    sso的端口必须改为8150，访问接口也必须与application.yml中的redirect_url完全一致，方可在本地测试成功；
