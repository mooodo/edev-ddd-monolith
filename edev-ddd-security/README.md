# 演示了通过DDD低代码平台实现用户认证授权的设计
这个项目是DDD低代码平台的实战应用，它通过`JWT + springboot security`，演示了用户认证授权的设计。

## authority模块
这是通过DDD领域建模实现的核心业务代码，实现了用户、角色、权限的增删改，以及它们直接的相互授权。
该模块DDD低代码平台`edev-ddd-support`，将领域模型直接转换成了程序设计的领域对象与服务。
通过DDD低代码平台可以很好地支持它们直接的多对多关系及其相应的操作。

在这个模块中的设计，所有的代码都是基于领域建模的业务代码设计，与具体的技术无关，
实现了整洁架构中将业务代码与技术框架解耦的设计思想。

## security模块
这是基于`JWT + springboot security`框架实现的用户认证授权。
该模块一方面调用了authority模块的核心业务代码，另一方面遵循`JWT + springboot security`框架的规范，
实现了对用户的认证及授权。

### jwt格式的token
1) 通过`/loglin`向`AuthController`发生登录请求，对用户密码进行认证。
2) 登录成功则调用`LoginSuccessHandler`，生成jwt格式的token，并返回前端。
3) 登录失败则调用`LoginFailureHandler`，返回错误信息。
4) 登录成功后，每次请求需要将token放到header的Authorization，后台会调用`JwtAuthenticationFilter`进行认证。
5) 如果认证失败，会调用`JwtAuthenticationEntryPoint`返回错误信息。

### 基于springboot security框架的认证授权
1) DefaultUserDetails: 基于该框架规范的业务实体
2) DefaultUserDetailsService: 通过它查询用户，并获取用户的授权列表
3) DefaultAuthenticationProvider: 基于该框架规范的认证提供者