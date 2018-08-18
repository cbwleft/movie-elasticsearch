package com.cbwleft.elasticsearch.entity;

import io.searchbox.annotations.JestId;
import lombok.Data;

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

    private int year;//年代

    private String origin;//产地

    private List<String> category;//类别

    private String releaseDate;//上映日期

    private float score;//豆瓣评分

    private int duration;//片长

    private String director;//导演

    private List<String> actor;//主演

    private String description;//简介

    private List<String> downloadUrl;//下载地址

    private Date updateDate;//更新时间

}
