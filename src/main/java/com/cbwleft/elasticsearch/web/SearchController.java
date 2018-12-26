package com.cbwleft.elasticsearch.web;

import com.cbwleft.elasticsearch.entity.Movie;
import com.cbwleft.elasticsearch.entity.Page;
import com.cbwleft.elasticsearch.entity.QueryDTO;
import com.cbwleft.elasticsearch.repository.IMovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.*;

@Controller
@Slf4j
public class SearchController {

    @Autowired
    private IMovieRepository movieRepository;

    @GetMapping("/")
    public String index(Model model) {
        QueryDTO queryDTO = QueryDTO.builder().minScore(7.5f).orderBy("updateDate").build();
        Page<Movie> page = movieRepository.query(queryDTO, 1, 6);
        List<String> recommendWord = Collections.emptyList();
        if (page != null) {
            recommendWord = page.getList().stream().map(Movie::getRecommendWord).collect(toList());
        }
        model.addAttribute("recommendWord", recommendWord);
        return "index";
    }

    @RequestMapping("/s")
    public String search(
            @RequestParam("wd") String queryString,
            @RequestParam(value = "pn", required = false, defaultValue = "1") int pageNo,
            Model model
    ) {
        log.info("搜索参数wd:{},pn:{}", queryString, pageNo);
        Page<Movie> page = movieRepository.query(queryString, pageNo, 10);
        model.addAttribute("page", page);
        model.addAttribute("wd", queryString);
        return "search";
    }

    @GetMapping("/detail/{id}")
    public String detail(
            @PathVariable("id") String id,
            Model model
    ) {
        Movie movie = movieRepository.get(id);
        model.addAttribute("movie", movie);
        return "detail";
    }
}
