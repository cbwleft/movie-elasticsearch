package com.cbwleft.elasticsearch.repository;

import com.cbwleft.elasticsearch.entity.Movie;
import com.cbwleft.elasticsearch.entity.Page;

public interface IMovieRepository {

    boolean save(Movie movie);

    Page<Movie> query(String queryString, int pageNo, int size);

    Movie get(String id);
}
