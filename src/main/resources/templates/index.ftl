<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>movie-elasticsearch</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/common.css">
</head>
<body class="container">
    <header>
        <h1 class="center-block">Movie ElasticSearch</h1>
    </header>
    <section>
        <form action="/s" class="input-group">
            <input name="wd" class="form-control">
            <div class="input-group-btn">
                <button type="submit" class="btn btn-primary">搜索</button>
            </div>
        </form>
        <p>热搜关键词：
            <a href="/s?wd=%E5%A4%8D%E4%BB%87%E8%80%85%E8%81%94%E7%9B%9F">复仇者联盟</a>
            <a href="/s?wd=%E6%AD%BB%E4%BE%8D2">死侍2</a>
            <a href="/s?wd=%E5%AF%82%E9%9D%99%E4%B9%8B%E5%9C%B0">寂静之地</a>
            <a href="/s?wd=%E9%93%B6%E7%BF%BC%E6%9D%80%E6%89%8B">银翼杀手</a>
            <a href="/s?wd=%E5%A4%B4%E5%8F%B7%E7%8E%A9%E5%AE%B6">头号玩家</a>
        </p>
    </section>
</body>
</html>