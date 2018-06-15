package com.eightmin4mile.goandroid.moviesapp2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Movie;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieResult;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieWebServiceProxy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by goandroid on 6/11/18.
 */

public class Utility {

    private static final String TAG = "Utility";

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
                    1,
                    movie.getRelease_date(),
                    false
            );

            movieEntries.add(newMovie);

        }

        return movieEntries;

    }

    public static void downloadMovies(final Context context,
                                      final MainViewModel viewModel){


        if(Utility.isNetworkAvailable(context)) {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {

                    List<MovieEntry> movieList =
                            getInternetMovies(MovieWebServiceProxy.SORT_BY_POPULARITY);
                    viewModel.setMovies(movieList);

                    if(movieList != null){

                        //callback.loadMovieData(movieList);
                        Log.d(TAG, "run: size = " + movieList.size());
                        Log.d(TAG, "run: " + movieList.toString());
                    }

                }
            });
        } else {
            Utility.showToastMessage(context,
                    "No Available internet connection");
        }
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
}
