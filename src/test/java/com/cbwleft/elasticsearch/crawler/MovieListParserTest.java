package com.cbwleft.elasticsearch.crawler;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MovieListParserTest {

    @Autowired
    private MovieListParser movieListParser;

    //@Test
    public void testParse() throws IOException {
        movieListParser.parse(MovieListParser.START_PAGE);
    }

}