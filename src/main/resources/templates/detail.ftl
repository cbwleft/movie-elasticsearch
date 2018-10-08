<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>movie-elasticsearch</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/common.css">
    <script type="text/javascript" src="/js/common.js"></script>
</head>
<body class="container">
    <h3>
        ${movie.name!""}
        <#if movie.translatedName??><#list movie.translatedName as tn>/${tn}</#list></#if>
        <#if movie.year??>(${movie.year})</#if>
    </h3>
    <#if movie.coverUrl??>
    <p><img src="${movie.coverUrl!}" class="img-thumbnail" onerror="notFound()"></p>
    </#if>
    <#if movie.director??>
    <p>导演：${movie.director}</p>
    </#if>
    <#if movie.actor??>
    <p>主演：<#list movie.actor as actor>${actor}<#if actor_has_next>/</#if></#list></p>
    </#if>
    <#if movie.category??>
    <p>类型：<#list movie.category as category>${category}<#if category_has_next>/</#if></#list></p>
    </#if>
    <#if movie.origin??>
    <p>制片地区：${movie.origin}</p>
    </#if>
    <#if movie.releaseDate??>
    <p>上映日期：${movie.releaseDate}</p>
    </#if>
    <#if movie.duration??>
    <p>片长：${movie.duration}分钟</p>
    </#if>
    <#if movie.score??>
    <p>豆瓣评分：${movie.score}</p>
    </#if>
    <#if movie.updateDay??>
    <p>更新日期：${movie.updateDay}</p>
    </#if>
    <#if movie.downloadUrl??>
    <section>
        <h4>下载地址:</h4>
        <#list movie.downloadUrl as du>
        <p><a href="${du}">${du}</a></p>
        </#list>
    </section>
    </#if>
</body>
</html>