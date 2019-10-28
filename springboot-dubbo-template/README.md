#微服务框架
##技术选型
 springboot、dubbo、mybatis-plus、swagger、P6Spy、druid、tx-lcn
##说明
####1、springboot对外提供restful服务
####2、dubbo进行微服务内部rpc调用
####3、mybatis-plus对mybatis操作数据库的轻量级封装，简化CRUD操作
####4、swaggger开发环境显示restful api,供前端开发人员在线浏览api文档并测试
####5、P6Spy开发环境显示sql格式化并进行性能分析
####6、durid数据库连接池，数据库操作监控
####7、tx-lcn分布式事务集成(需使用redis)

##ps
若要用于生产，请提前确认相关环境是否兼容springboot2.X,例如：网关

 
