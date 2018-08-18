package com.cbwleft.elasticsearch.jsoup;

import com.cbwleft.elasticsearch.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class MovieDetailParser {

    public static final String URL_PATTERN = "https://www.dy2018.com/i/{0}.html";

    public Movie parse(String id) throws IOException {
        URL url = new URL(MessageFormat.format(URL_PATTERN, id));
        Document document = Jsoup.parse(url, 10000);
        document.charset(Charset.forName("gb2312"));
        Movie movie = new Movie();
        movie.setId(id);
        String title = document.select(".title_all h1").text();
        log.debug("标题:{}", title);
        movie.setTitle(title);
        String score = document.select("strong.rank").text();
        log.debug("评分:{}", score);
        movie.setScore(Float.parseFloat(score));
        String updateDate = document.select("span.updatetime").text();
        log.debug("发布日期:{}", updateDate);
        try {
            movie.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd").parse(updateDate.replace("发布时间：", "")));
        } catch (ParseException e) {
            log.error("发布日期{}解析出错", updateDate);
        }
        Elements zoom = document.select("#Zoom");
        String coverUrl = zoom.select("p").get(0).select("img").attr("src");
        log.debug("封面图片:{}", coverUrl);
        movie.setCoverUrl(coverUrl);
        List<String> actor = new ArrayList<>();
        movie.setActor(actor);
        zoom.select("p").forEach(p -> {
            String text = p.text();
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
                movie.setDuration(Integer.parseInt(text.substring(6).split("\\s+")[0]));
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
                movie.setDescription(description.trim());
            }
        });
        List<String> downloadUrl = new ArrayList<>();
        movie.setDownloadUrl(downloadUrl);
        zoom.select("table a").forEach(a -> {
            String text = a.text();
            log.debug("下载地址:{}", text);
            downloadUrl.add(text);
        });
        return movie;
    }

}
