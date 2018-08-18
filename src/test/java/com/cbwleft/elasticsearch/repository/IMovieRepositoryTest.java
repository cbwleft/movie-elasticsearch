package com.cbwleft.elasticsearch.repository;

import com.cbwleft.elasticsearch.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IMovieRepositoryTest {

    @Autowired
    private IMovieRepository movieRepository;

    @Test
    public void query() {
        List<Movie> list = movieRepository.query("爆裂无声",1, 10);
        log.info(list.toString());
        Assert.assertNotNull(list);
    }
}