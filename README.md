# xultimate-crawler #

基于Redis实现的一套分布式定向抓取工程。

* 原本为工作期间自己实现的分布式定向抓取，原版基本无结构，完全为满足功能而设计。
* 提供LinkbaseHandler，作为链接库服务。LinkbaseHandlerSupport为其默认实现，其中包括预抓取Queue、更新Queue、已完成SortedSet、不可用SortedSet、通过Redis存储并将Redis作为链接库。
* 提供UrlResolveScheduler，作为调度器，参考Tomcat的JIOEndpoint实现方式。
* 提供UrlResolveExecutor，充当命令模式中的执行器功能，负责将URL分配给某个具体的UrlResolver。UrlResolveExecutorSupport为其默认实现。
* 提供StatusChecker，用于控制服务是否执行。监控线程也是通过改变它的状态来完成远程关闭功能。StatusCheckerSupport为其默认实现。
* 提供Startup，用于开启抓取服务。
* 提供Shutdown，用于远程关闭抓取服务。
* 提供UnableHandler，用于将不可用SortedSet中的数据加入到预抓取Queue中，实现重新处理功能。
* 提供UrlResolver接口，用于负责解析满足其规则的URL的功能。
* 提供AbstractUrlResolver，包含了优先级功能。级别越高，越优先匹配。还包含了LinkbaseHandler实例。
* 提供RestTemplateAbstractUrlResolver，内部包含RestTemplate实例，继承HttpComponent完成页面下载功能。
* 提供JsoupAbstractUrlResolver，用于具体解析已被Jsoup处理的URL的Document。可实现此类完成具体URL解析功能。
* 提供ShutdownMonitorThread，内部使用Netty作为服务端，处理远程关闭功能。
* 第一版远程关闭功能采用Socket/ServerSocket处理，第二版替换为Netty，Handler内单独采用Protobuf作为序列化和解序列化处理。此次将Netty部分重新整理并提取到xultimate-context工程，同时内置序列化和解序列化处理。

具体定向抓取展示。

* 测试包下包含了具体网站的抓取和解析处理，现已对具体的UrlResolver进行了加密处理，其他部分作为公开展示。
* 测试包下的shard由原来的简单的hash方式，改成采用xultimate-shard提供的服务处理。
* 测试包下的数据提炼存储由JdbcTemplate改成有MyBatis处理。
* 数据源采用RoutingDataSource处理，其无法支持分布式事物。
* RoutingProxyDataSourceFactoryBean也提供了RoutingDataSource的功能。且配合RoutingDataSourceTransactionManager利用Spring事物注解从使用习惯上满足分布式事物处理。在xultimate-jdbc工程中有展示。
* 测试包下的主键生成器和shard服务为演示方便，采用集成进来的方式，这两种服务都支持分布式的部署，可视情况而定。