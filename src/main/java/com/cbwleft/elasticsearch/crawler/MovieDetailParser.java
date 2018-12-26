package com.cbwleft.elasticsearch.crawler;

import com.cbwleft.elasticsearch.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.cbwleft.elasticsearch.crawler.MovieListParser.userAgentList;
import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class MovieDetailParser {

    public static final String URL_PATTERN = "https://www.dy2018.com/i/{0}.html";

    public Movie parse(String id) throws IOException {
        String userAgent = userAgentList[new Random().nextInt(userAgentList.length)];
        String url = MessageFormat.format(URL_PATTERN, id);
        Document document = Jsoup.connect(url).userAgent(userAgent).timeout(10000).get();
        Movie movie = new Movie();
        movie.setId(id);
        String title = document.select(".title_all h1").text();
        log.debug("标题:{}", title);
        movie.setTitle(title);
        String score = document.select("strong.rank").text();
        log.debug("评分:{}", score);
        try {
            movie.setScore(Float.parseFloat(score));
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
        }
        String updateDate = document.select("span.updatetime").text();
        log.debug("发布日期:{}", updateDate);
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            updateDate = updateDate.replace("发布时间：", "");
            movie.setUpdateDate(sdf.parse(updateDate));
            movie.setUpdateDay(LocalDate.parse(updateDate, dtf));
        } catch (ParseException e) {
            log.info("发布日期{}解析出错", updateDate);
        }
        Elements zoom = document.select("#Zoom");
        try {
            String coverUrl = zoom.select("p, div").get(0).select("img").attr("src");
            log.debug("封面图片:{}", coverUrl);
            movie.setCoverUrl(coverUrl);
        } catch (Exception e) {
            log.info("封面图片解析出错");
        }
        List<String> actor = new ArrayList<>();
        movie.setActor(actor);
        zoom.select("p, div").forEach(p -> {
            String text = p.text();
            try {
                if (text.startsWith("◎译　　名")) {
                    log.debug("译名:{}", text);
                    movie.setTranslatedName(Arrays.asList(text.substring(6).split("/")));
                } else if (text.startsWith("◎片　　名")) {
                    log.debug("片名:{}", text);
                    movie.setName(text.substring(6));
                } else if (text.startsWith("◎年　　代")) {
                    log.debug("年代:{}", text);
                    movie.setYear(Integer.parseInt(text.substring(6)));
                } else if (text.startsWith("◎产　　地")) {
                    log.debug("产地:{}", text);
                    movie.setOrigin(text.substring(6));
                } else if (text.startsWith("◎类　　别")) {
                    log.debug("类别:{}", text);
                    movie.setCategory(Arrays.asList(text.substring(6).split("/")));
                } else if (text.startsWith("◎片　　长")) {
                    log.debug("片长:{}", text);
                    movie.setDuration(Integer.parseInt(text.substring(6)
                            .replace("分钟", "").replace("Mins", "").trim()));
                } else if (text.startsWith("◎导　　演")) {
                    log.debug("导演:{}", text);
                    movie.setDirector(text.substring(6));
                } else if (text.startsWith("◎上映日期")) {
                    log.debug("上映日期:{}", text);
                    movie.setReleaseDate(text.substring(6));
                } else if (text.startsWith("◎主　　演") || text.startsWith("　　　　　")) {
                    log.debug("主演:{}", text);
                    actor.add(text.substring(6));
                } else if (text.startsWith("◎简　　介")) {
                    String description = p.nextElementSibling().text();
                    log.debug("简介:{}", description);
                    movie.setDescription(description.substring(2));
                }
            } catch (RuntimeException e) {
                log.info(e.getMessage());
            }
        });
        List<String> downloadUrl = zoom.select("table a").stream().map(Element::text).collect(toList());
        movie.setDownloadUrl(downloadUrl);
        return movie;
    }

}
