package com.cbwleft.elasticsearch.crawler;

import com.cbwleft.elasticsearch.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MovieDetailParserTest {

    @Autowired
    private MovieDetailParser movieDetail;

    @Test
    public void parse() throws IOException {
        Movie movie = movieDetail.parse("99860");
        log.info("电影详情:{}", movie);
        Assert.assertEquals(movie.getName(), "Skyscraper");
        Assert.assertEquals(movie.getDuration(), 102);
        Assert.assertEquals(movie.getYear(), 2018);
        Assert.assertEquals(movie.getOrigin(), "美国");
        Assert.assertNotNull(movie.getDescription());
        Assert.assertEquals(movie.getActor().size(), 23);
    }

}