package com.cbwleft.elasticsearch.repository;

import com.cbwleft.elasticsearch.entity.Movie;

public interface IMovieRepository {

    boolean save(Movie movie);
}
