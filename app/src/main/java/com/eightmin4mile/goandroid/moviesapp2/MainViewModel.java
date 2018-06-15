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

import java.util.List;

/**
 * Created by goandroid on 6/14/18.
 */

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    private LiveData<List<MovieEntry>> favorites;
    private MutableLiveData<List<MovieEntry>> movies;
    private Menu menu;
    AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "MainViewModel: Actively retrieving the tasks from the database");

    }

    public LiveData<List<MovieEntry>> getFavorites() {
        if(favorites==null){
            favorites = new MutableLiveData<>();
            // TODO should use background thread to retrieve data
            favorites = database.movieDao().loadAllMovies();
        }
        return favorites;
    }

    public LiveData<List<MovieEntry>> getMovies(){
        if(movies==null){
            movies= new MutableLiveData<>();
            loadMovies();
        }
        return movies;
    }

    public void setMovies(List<MovieEntry> newMovies){
        movies.setValue(newMovies);
    }

    private void loadMovies(){
        if(Utility.isNetworkAvailable(this.getApplication())) {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {

                    List<MovieEntry> movieList =
                            Utility.getInternetMovies(MovieWebServiceProxy.SORT_BY_POPULARITY);

                    if(movieList != null){

                        movies.postValue(movieList);
                        Log.d(TAG, "run: size = " + movieList.size());
                        Log.d(TAG, "run: " + movieList.toString());
                        Log.d(TAG, "run: movies' size() = " + movies.getValue().size());
                    }

                }
            });
        } else {
            Utility.showToastMessage(this.getApplication(),
                    "No Available internet connection");
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
