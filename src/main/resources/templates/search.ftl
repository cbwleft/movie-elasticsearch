<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>movie-elasticsearch</title>
    <script src="https://cdn.jsdelivr.net/npm/lazyload@2.0.0-beta.2/lazyload.js"></script>
</head>
<style>
    .cover{
        width: 160px;
        height: 220px;
        display: block;
    }
    em{
        color: orangered;
    }

</style>
<body>
    <header>
        <h1>Movie ElasticSearch</h1>
        <form action="/s">
            <input name="wd" value="${RequestParameters['wd']}">
            <button type="submit">搜索</button>
        </form>
    </header>
    <section>
        <#list list as movie>
            <div>
                <p>
                    <a href="/detail/${movie.id}">
                    ${movie.name!""}<#if movie.translatedName??><#list movie.translatedName as tn>/${tn}</#list></#if>
                    </a>
                </p>
                <p><img src="https://img3.doubanio.com/f/movie/03d3c900d2a79a15dc1295154d5293a2d5ebd792/pics/movie/tv_default_large.png"
                    <#if movie.coverUrl??>data-src="${movie.coverUrl}"</#if> class="cover lazyload" alt="${movie.name!""}" >
                </p>
                <p>${movie.description!"暂无简介"}</p>
            </div>
        </#list>
    </section>

</body>
<script>
    window.addEventListener("load", function(event) {
        lazyload();
    });
</script>
</html>