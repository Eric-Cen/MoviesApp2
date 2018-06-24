package com.eightmin4mile.goandroid.moviesapp2.retrofitMovie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by goandroid on 6/13/18.
 */

public class VideoResult {
    // http://api.themoviedb.org/3/movie/345287/videos?api_key="?api key?"
//    {
//        "id":345287,
//            "results":[
//                 {}
//        ]
//    }

    private long id;
    private List<Video> results;

    public VideoResult(long id, List<Video>videoList){
        this.id = id;
        this.results = new ArrayList<>(videoList);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public void add(Video video){
        if(results==null){
            results=new LinkedList<>();
        }

        results.add(video);
    }
}
