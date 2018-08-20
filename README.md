# movie-elasticsearch
### 简介
使用SpringBoot2.0+ElasticSearch+Jest实现的电影搜索网站<br>
[试一下](http://47.98.111.179)<br>

### 本地部署
* 启动ElasticSearch(我选择的是5.6.10版本)<br>
* 修改application.properties中的spring.elasticsearch.jest.uris参数<br>
* 启动SpringBoot项目<br>
* 访问<localhost:8080/crawl>开启爬虫<br>
* 访问<localhost:8080>开始搜索

### 为何要写这个demo
在学习ElasticSearch的过程中，发现ElasticSearch的java客户端多达十余种，使用两种不同的通讯协议，而且2.X和5.X版本差异较大。<br>
这种现象对于初学者来说不是非常友好，本来spring-data-elasticsearch应该是首要选择，但是我发现SpringBoot官方文档中首先推荐使用的是Jest，
而不是自家的data-elasticsearch。<br>
深入使用后发现，data-elasticsearch是基于TransportClient,而这个基于java序列化机制(9300端口)的，ElasticSearch官方准备废弃该客户端。
应该使用REST客户端(9200端口)代替。这就让Jest成了该项目的首选。

### 参考资料
* SpringBoot官方文档ElasticSearch部分 <https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/htmlsingle/#boot-features-elasticsearch>
* ElasticSearch TransportClient <https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/java-api.html>
