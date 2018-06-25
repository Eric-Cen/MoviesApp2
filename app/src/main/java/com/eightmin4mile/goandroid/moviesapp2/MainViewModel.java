package com.eightmin4mile.goandroid.moviesapp2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eightmin4mile.goandroid.moviesapp2.data.AppDatabase;
import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieWebServiceProxy;

import java.util.List;

/**
 * Created by goandroid on 6/14/18.
 */

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    private MutableLiveData<List<MovieEntry>> movies;
    AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "MainViewModel: Actively retrieving the tasks from the database");

    }


    public LiveData<List<MovieEntry>> getMovies(){
        if(movies==null){
            movies= new MutableLiveData<>();
            loadMovies();
        } else if (movies.getValue() == null){
            loadMovies();
        }

        return movies;
    }

    public void setMovies(List<MovieEntry> newMovies){
        movies.setValue(newMovies);
    }

    private void loadMovies(){
        // get sort order from preference
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

                }
            });
        } else if(Utility.isNetworkAvailable(this.getApplication())) {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {

                    List<MovieEntry> movieList =
                            Utility.getInternetMovies(sortByUrl);

                    if(movieList != null){
                        // search the existing movies in favorites,
                        // update boolean value for the new movie list
                        for(MovieEntry newMovie : movieList) {

                            //retrieve movie list from Favorites database by movie id
                            List<MovieEntry> movieEntries = database.movieDao()
                                    .loadMovieById(newMovie.getId());

                            boolean isAlreadyInFavorite;
                            if(movieEntries.size() >= 1) {  // the database already have same movie
                                isAlreadyInFavorite = true;
                            } else {
                                isAlreadyInFavorite=false;
                            }

                            if(isAlreadyInFavorite){
                                newMovie.setFavorites(true);
                            } else {
                                newMovie.setFavorites(false);
                            }
                        }

                        movies.postValue(movieList);
                    }
                }
            });

        } else {
            Utility.showToastMessage(this.getApplication(),
                    "No Available internet connection");
        }
    }

}
