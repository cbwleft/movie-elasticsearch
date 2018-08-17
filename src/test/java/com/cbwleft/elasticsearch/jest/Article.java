package com.cbwleft.elasticsearch.jest;

import io.searchbox.annotations.JestId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {

    @JestId
    private String id;

    private String author;

    private String content;
}
