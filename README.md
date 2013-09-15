# xultimate-crawler #

一个简单的分布式定向抓取应用，通过Redis记录链接库。


## 介绍 ##

工作期间自己实现的分布式定向抓取，后来提取出链接库部分，将抓取工作按模块（包）划分。

抓取部分主要通过Jedis记录抓取过的链接，Redis中包括抓取Queue、更新Queue、已完成Set、不可用Set，K/V链接。

抓取模块主要通过HttpClient作为下载器，Jsoup作为页面解析器。数据库部分采用单库分表。通过JdbcTemplate记录。

原来的关闭操作是通过Socket/ServerSocket交互。后改为Netty，只是想有一个Netty的相关Demo。

对具体的网页解析类（Listener）进行了加密处理。
