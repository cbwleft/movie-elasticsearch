package com.cbwleft.elasticsearch.entity;

import io.searchbox.annotations.JestId;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class Movie {

    @JestId
    private String id;

    private String name;//片名

    private String coverUrl;//封面图片

    private List<String> translatedName;//译名

    private String title;//标题

    private Integer year;//年代

    private String origin;//产地

    private List<String> category;//类别

    private String releaseDate;//上映日期

    private Float score;//豆瓣评分

    private Integer duration;//片长

    private String director;//导演

    private List<String> actor;//主演

    private String description;//简介

    private List<String> downloadUrl;//下载地址

    private Date updateDate;//更新时间

    private LocalDate updateDay;//更新日期

    public String getRecommendWord() {
        String word = getName();
        String title = getTitle();
        if (!StringUtils.isEmpty(title) && title.contains("《") && title.contains("》")) {
            word = title.substring(title.indexOf('《') + 1, title.indexOf('》'));
        }
        return word;
    }

}
