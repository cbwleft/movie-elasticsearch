package com.cbwleft.elasticsearch.entity;

import lombok.Data;

@Data
public class QueryDTO {

    private String query;

    private String origin;

    private Float minScore;

    private String category;

}
