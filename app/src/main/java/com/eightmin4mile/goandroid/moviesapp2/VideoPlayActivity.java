package com.eightmin4mile.goandroid.moviesapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Video;

public class VideoPlayActivity extends AppCompatActivity {
    private static final String TAG = "VideoPlayActivity";
    public static final String VIDEO_DATA = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);


        initViews();

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(VIDEO_DATA)){
            Video video = intent.getParcelableExtra(VIDEO_DATA);

            Log.d(TAG, "onCreate: video name = " + video.getName());
            Log.d(TAG, "onCreate: video id = " + video.getId());
        }
    }

    // initialize views
    private void initViews(){

    }
}
