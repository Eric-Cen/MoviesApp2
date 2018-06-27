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
    final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;

    final String REGION = "US";

    // sort by highest rating
    //final String SORT_BY_AVERAGE = "vote_average.desc";
    final String SORT_BY_TOP_RATED = "top_rated";

    // sort by most popular
    //final String SORT_BY_POPULARITY = "popularity.desc";
    final String SORT_BY_POPULARITY = "popular";


    // sort by favorites
    final String SORT_BY_FAVORITES = "favorites";



    // https://api.themoviedb.org/3/movie/top_rated?api_key=[replace_with_api_key]&region=US
    //https://api.themoviedb.org/3/movie/popular?api_key=[replace_with_api_key]&region=US
    @GET("/movie/{sort_by}")
    MovieResult getMovieResult(@Path("sort_by") String searchString,
                               @Query("api_key") String apiKey,
                               @Query("region") String region);


    // http://api.themoviedb.org/3/movie/345287/videos?api_key=[replace_with_api_key]
    @GET("/movie/{id}/videos")
    VideoResult getVideoList(@Path("id") long movieId,
                             @Query("api_key") String apiKey);



    // http://api.themoviedb.org/3/movie/345287/reviews?api_key=[replace_with_api_key]
    @GET("/movie/{id}/reviews")
    ReviewResult getReviewList(@Path("id") long movieId,
                               @Query("api_key") String apiKey);

}
