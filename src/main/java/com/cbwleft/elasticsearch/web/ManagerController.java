package com.cbwleft.elasticsearch.web;

import com.cbwleft.elasticsearch.crawler.MovieListParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@Slf4j
public class ManagerController {

    private static final ReentrantLock lock = new ReentrantLock();

    @Autowired
    private MovieListParser movieListParser;

    @GetMapping("/crawl")
    public String crawl (@RequestParam(value = "p",required = false, defaultValue = MovieListParser.START_PAGE) String page) {
        try {
            if (lock.tryLock()) {
                movieListParser.parse(page);
                return "爬虫执行完成";
            } else {
                return "请勿重复执行";
            }
        } catch (IOException e) {
            log.error("爬虫发生异常", e);
            return e.getMessage();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
