package com.eightmin4mile.goandroid.moviesapp2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Review;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Video;

import java.util.List;

/**
 * Created by goandroid on 6/18/18.
 */

public class DetailViewModel extends ViewModel {

    private static final String TAG = "DetailViewModel";

    private MutableLiveData<List<Video>> videos;
    private MutableLiveData<List<Review>> reviews;
    private MutableLiveData<MovieEntry> movieEntry;



    public LiveData<List<Video>> getVideos() {
        if(videos==null){
            videos = new MutableLiveData<>();
            loadVideoData();
        }
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos.setValue(videos);
    }

    public LiveData<List<Review>> getReviews() {
        if(reviews==null){
            reviews = new MutableLiveData<>();
            loadReviewData();
        }

        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews.setValue(reviews);
    }

    public LiveData<MovieEntry> getMovieEntry() {
        return movieEntry;
    }

    public void setMovieEntry(MovieEntry movieEntry) {
        if(this.movieEntry != null
                && this.movieEntry.getValue().getId() != movieEntry.getId()
                ) {
            return; // do nothing since it is the same movie data
        }

        if(this.movieEntry==null){
            this.movieEntry = new MutableLiveData<>();
        }

        this.movieEntry.setValue(movieEntry);


        loadVideoData();
        loadReviewData();
    }

    // load trailer video list from the internet
    private void loadVideoData() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Video> newList = Utility.getInternetVideos(movieEntry.getValue().getId());
                if(newList == null) {
                    Log.d(TAG, "loadVideoData: newList from retrofit is null");
                } else {
                    //Log.d(TAG, "loadVideoData: from retrofit, newList.size() = "
                      //  + newList.size());
                    videos.postValue(newList);
                }
            }
        });

    }

    // load review list from the internet
    private void loadReviewData(){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Review> reviewList = Utility.getInternetReviews(movieEntry.getValue().getId());
                if(reviewList == null) {
                    Log.d(TAG, "loadReviewData: reviewList is null");
                } else {
                  //  Log.d(TAG, "loadReviewData: from retrofit, reviewList.size() = "
                  //          + reviewList.size());
                    reviews.postValue(reviewList);
                }

            }
        });
    }

}
