# movie-elasticsearch
### 简介
使用SpringBoot2.0+ElasticSearch+Jest实现的电影搜索网站<br>
[试一下](http://s.cbwleft.top)<br>

### 本地部署
* 启动ElasticSearch(我选择的是5.6.10版本)
* 修改application.properties中的spring.elasticsearch.jest.uris参数
* 启动SpringBoot项目
* 访问<localhost:8080/crawl>开启爬虫
* 访问<localhost:8080>开始搜索

### Docker运行
* （可选，docker运行ElasticSearch）docker run -d -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -p 9200:9200 elasticsearch
* docker run -e spring.elasticsearch.jest.uris="http://ip:9200" -p 8080:8080 registry.cn-hangzhou.aliyuncs.com/cbwleft/movie-elasticsearch

### 为何要写这个demo
在学习ElasticSearch的过程中，发现ElasticSearch的java客户端多达十余种，使用两种不同的通讯协议，而且2.X和5.X版本差异较大。<br>
这种现象对于初学者来说不是非常友好，本来spring-data-elasticsearch应该是首要选择，但是我发现SpringBoot官方文档中首先推荐使用的是Jest，
其次才是自家的spring-data-elasticsearch。<br>
深入使用后发现，spring-data-elasticsearch基于TransportClient，这个客户端使用java序列化机制(9300端口)通讯，ElasticSearch官方准备废弃该客户端。
应该使用REST客户端(9200端口)代替。这就让Jest成了该项目的首选。

### 参考资料
* SpringBoot官方文档ElasticSearch部分 <https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/htmlsingle/#boot-features-elasticsearch>
* ElasticSearch TransportClient <https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/java-api.html>
* Jest官方文档 <https://github.com/searchbox-io/Jest/tree/master/jest>
* 个人整理的ElasticSearch学习思维导图 <http://naotu.baidu.com/file/3b7f1dec1a487abf6ffe51f1a950744b?token=e08546f6ca1fa320>
