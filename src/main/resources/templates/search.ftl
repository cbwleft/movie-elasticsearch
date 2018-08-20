<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>movie-elasticsearch</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/common.css">
    <script src="https://cdn.jsdelivr.net/npm/lazyload@2.0.0-beta.2/lazyload.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <style>
        em{
            color: orangered;
        }
        form{
            max-width: 280px;
            max-width: calc(100% - 100px);
        }
    </style>
</head>
<body class="container">
    <header>
        <h1><a href="/">Movie ElasticSearch</a></h1>
        <form action="/s" class="input-group">
            <input name="wd" class="form-control" value="${wd}">
            <div class="input-group-btn">
                <button type="submit" class="btn btn-primary">搜索</button>
            </div>
        </form>
        <a target="_blank" href="https://github.com/cbwleft"><img style="position: absolute; top: 0; right: 0; border: 0" src="https://camo.githubusercontent.com/38ef81f8aca64bb9a64448d0d70f1308ef5341ab/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f6461726b626c75655f3132313632312e706e67" alt="Fork me on GitHub" data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_darkblue_121621.png"></a>
    </header>
    <section>
        <p>共找到相关结果${page.total}个，耗时${page.took}ms</p>
    </section>
    <section>
        <#list page.list as movie>
        <div>
            <p>
                <a href="/detail/${movie.id}">
                ${movie.name!""}<#if movie.translatedName??><#list movie.translatedName as tn>/${tn}</#list></#if>
                </a>
            </p>
            <p><img
                    src = "https://img3.doubanio.com/f/movie/03d3c900d2a79a15dc1295154d5293a2d5ebd792/pics/movie/tv_default_large.png"
                    <#if movie.coverUrl??>data-src="${movie.coverUrl}"</#if>
                    class="img-thumbnail lazyload"
                    onerror="notFound()"
                >
            </p>
            <p>${movie.description!"暂无简介"}</p>
        </div>
        </#list>
    </section>
    <nav aria-label="...">
        <ul class="pager">
            <li class="previous<#if !page.hasPrevious()> disabled</#if>">
                <a href=<#if page.hasPrevious()>"/s?wd=${wd}&pn=${page.pageNo - 1}"<#else>#</#if>><span aria-hidden="true">&larr;</span> 上一页</a>
            </li>
            <li class="next<#if !page.hasNext()> disabled</#if>" >
                <a href=<#if page.hasNext()>"/s?wd=${wd}&pn=${page.pageNo + 1}"<#else>#</#if>>下一页 <span aria-hidden="true">&rarr;</span></a>
            </li>
        </ul>
    </nav>
</body>
<script>
    window.addEventListener("load", function(event) {
        lazyload();
    });
</script>
</html>