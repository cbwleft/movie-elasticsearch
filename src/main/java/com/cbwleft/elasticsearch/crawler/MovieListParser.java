package com.cbwleft.elasticsearch.crawler;

import com.cbwleft.elasticsearch.entity.Movie;
import com.cbwleft.elasticsearch.repository.IMovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class MovieListParser {

    public static final String START_PAGE = "https://www.dy2018.com/html/gndy/dyzz/index.html";

    @Autowired
    private MovieDetailParser movieDetailParser;

    @Autowired
    private IMovieRepository movieRepository;

    private Set<String> movieIds = new HashSet<>();

    public void parse(String url) throws IOException {
        Document document = Jsoup.parse(new URL(url), 10000);
        document.select(".co_content8 a").forEach(a -> {
            String href = a.attr("href");
            if (href.matches("/i/[0-9]+.html")) {
                String id = href.substring(3, href.lastIndexOf("."));
                if (movieIds.add(id)) {
                    log.info("开始抓取电影id:{}", id);
                    try {
                        Movie movie = movieDetailParser.parse(id);
                        movieRepository.save(movie);
                    } catch (IOException e) {
                        log.error("抓取电影id:{}异常", e);
                    }
                } else {
                    log.debug("电影id:{}已被采集，不再重复采集", id);
                }
            }
        });
        document.select("div.x a").forEach(a -> {
            String text = a.text();
            if (text.equals("下一页")) {
                try {
                    parse(a.absUrl("href"));
                } catch (IOException e) {
                    log.error("抓取下一页异常:{}", e);
                }
            }
        });
    }
}
