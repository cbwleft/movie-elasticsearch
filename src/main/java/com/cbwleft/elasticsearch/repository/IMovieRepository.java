package com.cbwleft.elasticsearch.repository;

import com.cbwleft.elasticsearch.entity.Movie;

import java.util.List;

public interface IMovieRepository {

    boolean save(Movie movie);

    List<Movie> query(String queryString, int pageNo, int size);
}
