package com.cbwleft.elasticsearch.repository.impl;

import com.cbwleft.elasticsearch.entity.Movie;
import com.cbwleft.elasticsearch.repository.IMovieRepository;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@Slf4j
public class MovieESRepository implements IMovieRepository {

    public static final String INDEX = "movie";

    public static final String TYPE = "dy2018";

    @Autowired
    private JestClient client;

    @Override
    public boolean save(Movie movie) {
        Index index = new Index.Builder(movie).index(INDEX).type(TYPE).build();
        try {
            JestResult jestResult = client.execute(index);
            log.info("save返回结果{}", jestResult.getJsonString());
            return jestResult.isSucceeded();
        } catch (IOException e) {
            log.error("save异常", e);
            return false;
        }
    }
}
