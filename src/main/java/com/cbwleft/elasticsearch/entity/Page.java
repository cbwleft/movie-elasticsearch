package com.cbwleft.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Page<T> {

    private int pageNo;

    private int size;

    private List<T> list;

    private long total;

    private int took;

    public boolean hasNext() {
        if (pageNo * size >= total) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasPrevious() {
        if (pageNo > 1) {
            return true;
        } else {
            return false;
        }
    }

}
