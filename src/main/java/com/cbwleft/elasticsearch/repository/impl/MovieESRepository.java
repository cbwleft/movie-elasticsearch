package com.cbwleft.elasticsearch.repository.impl;

import com.cbwleft.elasticsearch.entity.Movie;
import com.cbwleft.elasticsearch.entity.Page;
import com.cbwleft.elasticsearch.entity.QueryDTO;
import com.cbwleft.elasticsearch.repository.IMovieRepository;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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

    @Override
    public Page<Movie> query(String queryString, int pageNo, int size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false).tagsSchema("default");
        searchSourceBuilder.highlighter(highlightBuilder);
        QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(queryString);
        queryStringQueryBuilder
                .field("name", 10)
                .field("translatedName", 8)
                .field("director", 5)
                .field("actor", 3)
                .field("description");
        searchSourceBuilder.query(queryStringQueryBuilder).from(from(pageNo, size)).size(size);
        log.debug("搜索DSL:{}", searchSourceBuilder.toString());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(INDEX)
                .addType(TYPE)
                .build();
        try {
            SearchResult result = client.execute(search);
            List<SearchResult.Hit<Movie, Void>> hits = result.getHits(Movie.class);
            List<Movie> movies = hits.stream().map(hit -> {
                Movie movie = hit.source;
                Map<String, List<String>> highlight = hit.highlight;
                if (highlight.containsKey("name")) {
                    movie.setName(highlight.get("name").get(0));
                }
                if (highlight.containsKey("translatedName")) {
                    movie.setTranslatedName(highlight.get("translatedName"));
                }
                return movie;
            }).collect(toList());
            int took = result.getJsonObject().get("took").getAsInt();
            Page<Movie> page = Page.<Movie>builder().list(movies).pageNo(pageNo).size(size).total(result.getTotal()).took(took).build();
            return page;
        } catch (IOException e) {
            log.error("search异常", e);
            return null;
        }

    }

    @Override
    public Page<Movie> query(QueryDTO queryDTO, int pageNo, int size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().from(from(pageNo, size)).size(size);
        if (queryDTO.getMinScore() != null) {
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("score").gte(queryDTO.getMinScore());
            boolQueryBuilder.must(rangeQueryBuilder);
            searchSourceBuilder.query(boolQueryBuilder);
        }
        if (queryDTO.getOrderBy() != null) {
            searchSourceBuilder.sort(queryDTO.getOrderBy(), SortOrder.DESC);
        }
        log.debug("搜索DSL:{}", searchSourceBuilder.toString());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(INDEX)
                .addType(TYPE)
                .build();
        try {
            SearchResult result = client.execute(search);
            List<Movie> movies = result.getSourceAsObjectList(Movie.class, false);
            int took = result.getJsonObject().get("took").getAsInt();
            Page<Movie> page = Page.<Movie>builder().list(movies).pageNo(pageNo).size(size).total(result.getTotal()).took(took).build();
            return page;
        } catch (IOException e) {
            log.error("search异常", e);
            return null;
        }

    }

    @Override
    public Movie get(String id) {
        Get get = new Get.Builder(INDEX, id).type(TYPE).build();
        try {
            JestResult result = client.execute(get);
            Movie movie = result.getSourceAsObject(Movie.class);
            return movie;
        } catch (IOException e) {
            log.error("get异常", e);
            return null;
        }
    }

    private int from(int pageNo, int size) {
        return (pageNo - 1) * size < 0 ? 0 : (pageNo - 1) * size;
    }
}
