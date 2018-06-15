package com.eightmin4mile.goandroid.moviesapp2.retrofitMovie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by goandroid on 6/12/18.
 */

public class MovieResult {

    private int page;

    private int total_pages;
    private int total_results;

    List<Movie> results;

    public MovieResult(int page, int total_pages, int total_results, List<Movie>movieList){
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = new ArrayList<>(movieList);

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public void add(Movie movie){
        if(results==null){
            results = new LinkedList<>();
        }

        results.add(movie);
    }

}
