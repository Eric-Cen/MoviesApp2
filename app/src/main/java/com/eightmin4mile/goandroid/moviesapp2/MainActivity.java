package com.eightmin4mile.goandroid.moviesapp2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.eightmin4mile.goandroid.moviesapp2.data.AppDatabase;
import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO
    // 1) Use AppExcutors to get the movie data from web
    // 2) display movie data in grid view
    // 3) add movieResult to live data? or just store MovieEntry with ROOM
    // 3) create a MovieDetail activity
    // 4) retrieve video list by movie id
    // 5) list videos under MovieDetail activity
    // 6) implement click to play video from youtube api


    private static final String TAG = "MainActivity";

    private AppDatabase mDb;
    private MovieAdapter mAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.grid_view_movies);
        mAdapter = new MovieAdapter(this,
                R.layout.grid_item_layout);

        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MovieEntry movie = mAdapter.getItem(position);
                Log.d(TAG, "onItemClick: id: " + movie.getId() +
                            " \nname: " + movie.getTitle());
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();


        //
        // TODO
        // Option 1: to load data from ROOM database
        // Option 2: to load data from the internet
        // TODO 3 load detail view with movie id
        // TODO 4 implement video data, recycleView for video data
        // TODO 5 implement video player (youtube)

    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {
                Log.d(TAG, "onChanged: Updating list of movies from LiveData in ViewModel");
              mAdapter.setMovies(movieEntries);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MainViewModel vm = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        vm.setMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sort:
                return true;
            case R.id.action_most_popular:
                MainViewModel vm = ViewModelProviders.of(this)
                        .get(MainViewModel.class);
                Menu menu = vm.getMenu();
                menu.findItem(R.id.action_sort).setTitle("Sort by: Most Popular");
                //TODO: set preference,
                //TODO: get the list from internet,
                //TODO: hide this menu item
                return true;
            case R.id.action_highest_rated:
                return true;
            case R.id.action_favorites:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //TODO: read from preference
        //TODO: hide active menu
        //TODO: update text on SORT BY: ???
        return super.onPrepareOptionsMenu(menu);
    }
}
