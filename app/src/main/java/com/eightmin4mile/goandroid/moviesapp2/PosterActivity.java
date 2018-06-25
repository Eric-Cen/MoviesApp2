package com.eightmin4mile.goandroid.moviesapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Activity to show the full-size poster
 */
public class PosterActivity extends AppCompatActivity {

    private ImageView mMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        mMoviePoster = (ImageView)findViewById(R.id.iv_full_poster);

        String path = getIntent().getStringExtra("full_image");

        Picasso.with(getApplicationContext())
                .load(path)
                .error(R.drawable.image_not_available)
                .into(mMoviePoster);
    }
}
