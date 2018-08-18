package com.cbwleft.elasticsearch.jest;

import io.searchbox.annotations.JestId;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Article {

    @JestId
    private String id;

    private String author;

    private String content;

    private Date date;

}
