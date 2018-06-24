package com.eightmin4mile.goandroid.moviesapp2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieWebServiceProxy;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Video;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayActivity extends YouTubeFailureRecoveryActivity {
    private static final String TAG = "VideoPlayActivity";
    public static final String VIDEO_DATA = "video";
    private Video myVideo;

    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        initializeViews();
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(VIDEO_DATA)){
            myVideo = intent.getParcelableExtra(VIDEO_DATA);

//            Log.d(TAG, "onCreate: video name = " + myVideo.getName());
//            Log.d(TAG, "onCreate: video id = " + myVideo.getId());
//            Log.d(TAG, "onCreate: video key = " + myVideo.getKey());


            youTubePlayerView.initialize(MovieWebServiceProxy.YOUTUBE_API_KEY,
                    this);
        }
    }

    private void initializeViews(){
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        if(!wasRestored){
            if(myVideo != null) {
                youTubePlayer.cueVideo(myVideo.getKey());
            } else {
                Utility.showToastMessage(getApplicationContext(), "Invalid video link!!");
            }
        }

    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
