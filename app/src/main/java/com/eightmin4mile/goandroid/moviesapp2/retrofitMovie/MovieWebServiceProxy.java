package com.eightmin4mile.goandroid.moviesapp2.retrofitMovie;

import com.eightmin4mile.goandroid.moviesapp2.BuildConfig;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by goandroid on 6/12/18.
 */

public interface MovieWebServiceProxy {
    // URL to the Movie Web service to use with the Retrofit service
    final String sMovie_Service_URL_Retro = "http://api.themoviedb.org/3";
    final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    final String SORT_BY_AVERAGE = "vote_average.desc";
    final String SORT_BY_POPULARITY = "popularity.desc";


    /**
     * Method used to query the movie service web service
     * http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=71e405c4b1b4939e2a7ae33b8471fc17
     * http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=71e405c4b1b4939e2a7ae33b8471fc17
     */

    @GET("/discover/movie")
    MovieResult getMovieResult(@Query("sort_by") String searchString,
                               @Query("api_key") String apiKey);


/*
@GET("/movie/{id}/videos")
VideoResult getVideoList(@Path("id") long movieId,
                           @Query("api_key") String apiKey);

 */
    // http://api.themoviedb.org/3/movie/345287/videos?api_key=71e405c4b1b4939e2a7ae33b8471fc17
}
