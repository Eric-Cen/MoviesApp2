package com.eightmin4mile.goandroid.moviesapp2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.eightmin4mile.goandroid.moviesapp2.data.AppDatabase;
import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.MovieWebServiceProxy;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Review;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Video;
import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.VideoResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static java.util.logging.Logger.global;

public class DetailActivity extends AppCompatActivity
        implements VideoAdapter.ItemClickListener {

    // 1) show movie details - completed
    // 2) retrieve videos list from internet, with retrofit - completed
    // 3) show videos list into RecyclerView - completed
    //TODO 4) play video in YouTube API
    // 5) allow user to add a movie in favorite list - completed

    private static final String TAG = "DetailActivity";
    private final String imageSizeOriginal = "original/";

    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mMoviePlot;
    private ImageView mMoviePoster;
    private ToggleButton mFavorites;

    private TextView trailerTextView;
    private TextView reviewTextView;

    String thumbnailPosterPath = "";
    String originalPosterPath = "";

    private VideoAdapter videoAdapter;
    private RecyclerView videoRecyclerView;

    private ReviewAdapter reviewAdapter;
    private RecyclerView reviewRecyclerView;

    private DetailViewModel detailViewModel;

    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Movie detail:");
        initializeView();

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        videoAdapter = new VideoAdapter(getApplicationContext(),
                DetailActivity.this);
        videoRecyclerView.setAdapter(videoAdapter);


        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewAdapter = new ReviewAdapter(getApplicationContext());
        reviewRecyclerView.setAdapter(reviewAdapter);


        mDb = AppDatabase.getInstance(getApplicationContext());

        MovieEntry movie = getIntent().getParcelableExtra(MainActivity.MOVIE_INFO);

        if (movie != null) {
            mTitle.setText(movie.getTitle());
            mReleaseDate.setText(movie.getReleaseDate());
            mVoteAverage.setText("movie.getVote_average() +  movie.getVote_count() ");
            mMoviePlot.setText(movie.getSynopsis());

            mFavorites.setChecked(movie.isFavorites());

            thumbnailPosterPath = MovieAdapter.baseImageURL
                    + MovieAdapter.thumbnailSize
                    + movie.getPoster();

            originalPosterPath = MovieAdapter.baseImageURL
                    + imageSizeOriginal
                    + movie.getPoster();

            Picasso.with(getApplicationContext())
                    .load(thumbnailPosterPath)
                    .error(R.drawable.image_not_available)
                    .into(mMoviePoster);

            setupDetailViewModel(movie);

        } else {
            Utility.showToastMessage(getApplicationContext(), "The movie object is null");
        }

    }

    private void initializeView() {
        mTitle = (TextView) findViewById(R.id.original_title);

        mReleaseDate = (TextView) findViewById(R.id.release_date);

        mVoteAverage = (TextView) findViewById(R.id.vote_avg);

        mMoviePlot = (TextView) findViewById(R.id.plot_synopsis);

        trailerTextView = (TextView) findViewById(R.id.tv_detail_trailers);
        reviewTextView = (TextView) findViewById(R.id.tv_detail_review);

        mMoviePoster = (ImageView) findViewById(R.id.movie_poster);

        mMoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (originalPosterPath != null && (!originalPosterPath.equals(""))) {
                    // pass the path as a string to a new activity
                    // show the full poster image
                    Intent intent = new Intent(getApplicationContext(), PosterActivity.class);
                    intent.putExtra("full_image", originalPosterPath);
                    startActivity(intent);
                } else {
                    Utility.showToastMessage(getApplicationContext(), "Invalid Image link.");
                }
            }
        });

        mFavorites = findViewById(R.id.tg_favorite);
        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean stage = mFavorites.isChecked();
                MovieEntry movie = detailViewModel.getMovieEntry().getValue();

                if (stage) {
                    mFavorites.setChecked(true);
                    Log.d(TAG, "onClick: set to true");
                    // add movie data to ROOM database
                    // if it is not already in favorites list
                    if (!movie.isFavorites()) {
                        addToFavorite(movie);
                    } else {
                        Utility.showToastMessage(getApplicationContext(),
                                "this movie is already in Favorites database. No add acction is performed");
                    }
                } else {
                    mFavorites.setChecked(false);
                    // remove movie data from ROOM database
                    removeFromFavorite(movie);
                    Log.d(TAG, "onClick: set to false");
                }

            }
        });
        videoRecyclerView = findViewById(R.id.rv_video);
        reviewRecyclerView = findViewById(R.id.rv_review);


    }

    private void setupDetailViewModel(MovieEntry movieEntry) {
        detailViewModel = ViewModelProviders.of(this)
                .get(DetailViewModel.class);
        detailViewModel.setMovieEntry(movieEntry);
        detailViewModel.getVideos().observe(DetailActivity.this, new Observer<List<Video>>() {
            @Override
            public void onChanged(@Nullable List<Video> videos) {
                Log.d(TAG, "onChanged: updating video list from LiveData in DetailViewModel");

                videoAdapter.setVideoList(videos);

                int size = 0;
                if(videos != null) {
                    size = videos.size();
                }
                trailerTextView.setText("Trailers (" + size + "):");
            }
        });

        detailViewModel.getReviews().observe(DetailActivity.this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviewList) {
                Log.d(TAG, "onChanged: updating review list from LiveData in DetailViewModel");

                reviewAdapter.setReviewList(reviewList);
                int size = 0;
                if(reviewList != null){
                    size = reviewList.size();
                }
                reviewTextView.setText("Reviews (" + size + "):");
            }
        });
    }


    @Override
    public void onItemClickListener(int itemId) {
        Video video = videoAdapter.getItem(itemId);
        Log.d(TAG, "onItemClickListener: video is " + video.getName());

        //completed - convert Vidoe object to parcelable
        //TODO start a new intent to pass video as an object
        //TODO on a new activity, play the video with youtube api

        Intent intent = new Intent(getApplicationContext(),
                VideoPlayActivity.class);
        intent.putExtra(VideoPlayActivity.VIDEO_DATA,
                video);
        startActivity(intent);
    }

    public void addToFavorite(final MovieEntry movieEntry) {

        movieEntry.setFavorites(true);

        //TODO how to update movie entry for gridview adapter
        // user new launch mode?
        // or use ROOM db observe


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insertMovie(movieEntry);
                Log.d(TAG, movieEntry.getTitle() + " is added to ROOM DB");
            }
        });

    }

    public void removeFromFavorite(final MovieEntry movieEntry) {

        //TODO how to update movie entry for gridview adapter
        // user new launch mode?
        // or use ROOM db observe
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().deleteMovie(movieEntry);
                Log.d(TAG, movieEntry.getTitle() + " is deleted from ROOM DB");
                movieEntry.setFavorites(false);
            }
        });
    }

}
