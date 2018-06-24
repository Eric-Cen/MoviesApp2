package com.eightmin4mile.goandroid.moviesapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Movie;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieResult;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieWebServiceProxy;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Review;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.ReviewResult;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Video;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.VideoResult;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by goandroid on 6/11/18.
 */

public class Utility {

    private static final String TAG = "Utility";

    // Constants for spinner position
    private static final int SORT_MOST_POPULAR = 0;
    private static final int SORT_HIGHEST_RATING = 1;
    private static final int SORT_FAVORITES = 2;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToastMessage(Context context,
                                        String msg){
        Toast.makeText(context,
                msg,
                Toast.LENGTH_SHORT).show();
    }

    public static List<MovieEntry> getMovieEntryList(List<Movie> movies){
        List<MovieEntry> movieEntries = new ArrayList<>();

        // TODO how to present doulbe or long in ROOM database
        for ( Movie movie : movies) {
            MovieEntry newMovie = new MovieEntry(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getPoster_path(),
                    movie.getOverview(),
                    1, //TODO
                    movie.getRelease_date(),
                    false
            );

            movieEntries.add(newMovie);

        }

        return movieEntries;

    }


    public static List<MovieEntry> getInternetMovies(String sortOrder){
        MovieWebServiceProxy mMovieWebServiceProxy =
                new RestAdapter.Builder()
                        .setEndpoint(MovieWebServiceProxy.sMovie_Service_URL_Retro)
                        .build()
                        .create(MovieWebServiceProxy.class);

        MovieResult movieResult = null;

        try {
            movieResult = mMovieWebServiceProxy
                    .getMovieResult(sortOrder,
                            MovieWebServiceProxy.API_KEY);
        } catch (RetrofitError e){
            Log.d(TAG, "RetrofitError: " + e.getMessage());
        }

        return movieResult == null ?
                null : Utility.getMovieEntryList(movieResult.getResults());

    }

    public static List<Video> getInternetVideos(long id){
        MovieWebServiceProxy movieWebServiceProxy =
                new RestAdapter.Builder()
                        .setEndpoint(MovieWebServiceProxy.sMovie_Service_URL_Retro)
                        .build()
                        .create(MovieWebServiceProxy.class);

        VideoResult videoResult = null;

        try {
            videoResult = movieWebServiceProxy
                    .getVideoList(id,
                            MovieWebServiceProxy.API_KEY);
        } catch (RetrofitError e){
            Log.d(TAG, "RetrofitError: " + e.getMessage());
        }

        if(videoResult==null){
            Log.d(TAG, "getInternetVideos: null return from retrofit");
        } else {
            Log.d(TAG, "getInternetVideos: videoResult.getResults().size() = "
                    + videoResult.getResults().size());
        }


        return videoResult == null ? null : videoResult.getResults();
    }

    public static List<Review> getInternetReviews(long id){
        MovieWebServiceProxy movieWebServiceProxy =
                new RestAdapter.Builder()
                .setEndpoint(MovieWebServiceProxy.sMovie_Service_URL_Retro)
                .build()
                .create(MovieWebServiceProxy.class);

        ReviewResult reviewResult = null;

        try {

            reviewResult = movieWebServiceProxy
                    .getReviewList(id,
                            MovieWebServiceProxy.API_KEY);
        } catch (RetrofitError e){
            Log.d(TAG, "RetrofitError: " + e.getMessage());
        }

        if(reviewResult == null){
            Log.d(TAG, "getInternetReviews: null return from retrofit");
        } else {
            Log.d(TAG, "getInternetReviews: videoResult.getResults().size() = "
                    + reviewResult.getResults().size());
        }

        return  reviewResult == null ? null : reviewResult.getResults();
    }

    public static void setSortOrder(Context context, String sortBy){
        SharedPreferences preference = context.getSharedPreferences("com.eightmin4mile.goandroid.moviesapp2",
                Context.MODE_PRIVATE);

        preference.edit().putString("SORT_BY", sortBy).apply();

    }

    public static String getSortOrder(Context context){
        SharedPreferences preference = context.getSharedPreferences("com.eightmin4mile.goandroid.moviesapp2",
                Context.MODE_PRIVATE);

        String sortOrder = preference.getString("SORT_BY", "Most Popular");
        return sortOrder;
    }

    public static String getSortOrderUrl(Context context, String sortBy){
        String returnVal;

        if(sortBy.equals(context.getApplicationContext()
                .getResources()
                .getString(R.string.sort_by_popularity))){
            returnVal = MovieWebServiceProxy.SORT_BY_POPULARITY;

        } else if(sortBy.equals(context.getApplicationContext()
                .getResources()
                .getString(R.string.sort_by_rating))){
            returnVal = MovieWebServiceProxy.SORT_BY_AVERAGE;
        } else {
            // default value
            returnVal = MovieWebServiceProxy.SORT_BY_FAVORITES;
        }

        return returnVal;
    }

    public static int getSortOrderPosition(Context context, String sortBy){
        int position;
        if(sortBy.equals(context.getApplicationContext()
                .getResources()
                .getString(R.string.sort_by_popularity))){
            position = SORT_MOST_POPULAR; // Most Popular

        } else if(sortBy.equals(context.getApplicationContext()
                .getResources()
                .getString(R.string.sort_by_rating))){
            position = SORT_HIGHEST_RATING; // Highest Rating
        } else {
            // default value
            position = SORT_FAVORITES; // Favorites
        }


        return position;

    }
}
