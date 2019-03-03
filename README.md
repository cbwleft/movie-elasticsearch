# movie-elasticsearch
### 简介
使用 SpringBoot2.0 + ElasticSearch + Jest 实现的电影搜索网站<br>
[试一下](http://s.cbwleft.top)<br>

### 本地部署
* 启动 ElasticSearch 6.X+
* 修改 application.properties 中的 spring.elasticsearch.jest.uris 参数
* 启动 SpringBoot 项目
* 访问 <localhost:8080/crawl> 开启爬虫
* 访问 <localhost:8080> 开始搜索

### Docker 运行（需要先行安装 ElasticSearch）
docker run -e spring.elasticsearch.jest.uris="http://elasticsearch:9200" -p 8080:8080 cbwleft/movie-elasticsearch

### Docker Compose 运行（包含 ElasticSearch）
进入项目根目录运行：docker-compose up

### 为何要写这个 demo
在学习 ElasticSearch 的过程中，发现 ElasticSearch 的 Java 客户端选择太多，使用两种不同的通讯协议，而且 2.X 和 5.X+ 版本差异较大。<br>
这种现象对于选择困难症患者来说不是非常友好，本来 spring-data-elasticsearch 应该是首要选择，但是我发现 SpringBoot 官方文档中首先推荐使用的是 Jest，
其次才是自家的 spring-data-elasticsearch。<br>
深入使用后发现，spring-data-elasticsearch 基于 TransportClient，这个客户端使用 Java 序列化机制(9300 端口)通讯，ElasticSearch 官方准备废弃该客户端。<br>
应该使用 REST 客户端(9200 端口)代替。这就让 Jest 成了该项目的首选。

### 参考资料
* [SpringBoot 官方文档 ElasticSearch 部分](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-elasticsearch)
* [ElasticSearch TransportClient](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-api.html)
* [Jest 官方文档](https://github.com/searchbox-io/Jest/tree/master/jest)
* [个人整理的 ElasticSearch 学习思维导图](http://naotu.baidu.com/file/3b7f1dec1a487abf6ffe51f1a950744b?token=e08546f6ca1fa320)
