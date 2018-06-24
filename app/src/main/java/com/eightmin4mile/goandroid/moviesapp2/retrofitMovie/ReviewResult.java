package com.eightmin4mile.goandroid.moviesapp2.retrofitMovie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by goandroid on 6/23/18.
 */

public class ReviewResult {

//    {
//        "id":345287,
//        "page":1,
//        "results":[
//          {
//          }
//        ],
//        "total_pages":1,
//        "total_results":1
//    }

    private long id;
    private int page;
    private List<Review> results;
    private int total_pages;
    private int total_results;

    public ReviewResult(long id,
                        int page,
                        List<Review>reviewList,
                        int total_pages,
                        int total_results){
        this.id = id;
        this.page = page;
        this.results = new ArrayList<>(reviewList);
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
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

    public void add(Review review){
        if(results == null){
            results = new LinkedList<>();
        }

        results.add(review);
    }
}
