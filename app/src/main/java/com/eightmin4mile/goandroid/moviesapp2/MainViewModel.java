package com.eightmin4mile.goandroid.moviesapp2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;

import com.eightmin4mile.goandroid.moviesapp2.data.AppDatabase;
import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieWebServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goandroid on 6/14/18.
 */

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

   // private LiveData<List<MovieEntry>> favorites;
    private MutableLiveData<List<MovieEntry>> movies;
    AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "MainViewModel: Actively retrieving the tasks from the database");

    }


    public LiveData<List<MovieEntry>> getMovies(){
        Log.d(TAG, "getMovies: here");
        if(movies==null){
            Log.d(TAG, "getMovies: null");
            movies= new MutableLiveData<>();
            loadMovies();
        } else if (movies.getValue() == null){
            Log.d(TAG, "getMovies: getValue() is null");
            loadMovies();
        } else {
            Log.d(TAG, "getMovies: not null");
        }
        return movies;
    }

    public void setMovies(List<MovieEntry> newMovies){
        movies.setValue(newMovies);
    }

    private void loadMovies(){
        final String sortByUrl = Utility.getSortOrderUrl(this.getApplication(),
                Utility.getSortOrder(this.getApplication()));

        Log.d(TAG, "loadMovies: sortBy = " + sortByUrl);

        // load from local ROOM database
        if(sortByUrl.equals(MovieWebServiceProxy.SORT_BY_FAVORITES)){

            // load from the internet for sort orders: Most Popular and Highest Rating
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    movies.postValue(database.movieDao()
                            .loadAllMovies());
                    Log.d(TAG, "run: in diskIO: " + sortByUrl);

                }
            });
        } else if(Utility.isNetworkAvailable(this.getApplication())) {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {

                    List<MovieEntry> movieList =
                            Utility.getInternetMovies(sortByUrl);

                    if(movieList != null){

                        int i=0;
                        // search the existing movies in favorites,
                        // update boolean value for the new movie list
                        for(MovieEntry newMovie : movieList) {
                            List<MovieEntry> movieEntries = database.movieDao()
                                    .loadMovieById(newMovie.getId());
                            boolean isAlreadyInFavorite;
                            if(movieEntries.size() >= 1) {
                                isAlreadyInFavorite = true;
                            } else {
                                isAlreadyInFavorite=false;
                            }

                            if(isAlreadyInFavorite){
                              //  Log.d(TAG, "updating favorites: set to true");
                                newMovie.setFavorites(true);
                            } else {
                                newMovie.setFavorites(false);
                               // Log.d(TAG, "updating favorites to false");
                            }

                          //  Log.d(TAG, "updating favorites: " + i + " = " + isAlreadyInFavorite);
                            ++i;

                        }

                        int j=0;
                        for(MovieEntry mm : movieList){
                           // Log.d(TAG, "after updating favorites: " + j + " = " + mm.isFavorites());
                            j++;
                        }
                        movies.postValue(movieList);

                        Log.d(TAG, "run: " + sortByUrl + " size = " + movieList.size());
                        Log.d(TAG, "run: " + movieList.toString());
                        //Log.d(TAG, "run: movies' size() = " + movies.getValue().size());

                        // check if the movie entry is already in the Favorites DB or not

                    }
                }
            });

        } else {
            Utility.showToastMessage(this.getApplication(),
                    "No Available internet connection");
        }
    }

}
