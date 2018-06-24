package com.eightmin4mile.goandroid.moviesapp2.retrofitMovie;

import com.eightmin4mile.goandroid.moviesapp2.BuildConfig;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by goandroid on 6/12/18.
 */

public interface MovieWebServiceProxy {
    // URL to the Movie Web service to use with the Retrofit service
    final String sMovie_Service_URL_Retro = "http://api.themoviedb.org/3";
    final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    // sort by highest rating
    final String SORT_BY_AVERAGE = "vote_average.desc";

    // sort by most popular
    final String SORT_BY_POPULARITY = "popularity.desc";

    // sort by favorites
    final String SORT_BY_FAVORITES = "favorites";


    /**
     * Method used to query the movie service web service
     * http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=[replace_with_api_key]
     * http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[replace_with_api_key]
     */

    @GET("/discover/movie")
    MovieResult getMovieResult(@Query("sort_by") String searchString,
                               @Query("api_key") String apiKey);


    // http://api.themoviedb.org/3/movie/345287/videos?api_key=[replace_with_api_key]
    @GET("/movie/{id}/videos")
    VideoResult getVideoList(@Path("id") long movieId,
                             @Query("api_key") String apiKey);



    // http://api.themoviedb.org/3/movie/345287/reviews?api_key=[replace_with_api_key]
    @GET("/movie/{id}/reviews")
    ReviewResult getReviewList(@Path("id") long movieId,
                               @Query("api_key") String apiKey);

}
