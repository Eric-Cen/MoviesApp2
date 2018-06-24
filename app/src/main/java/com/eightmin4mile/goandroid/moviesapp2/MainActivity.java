package com.eightmin4mile.goandroid.moviesapp2;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

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
    public static final String MOVIE_INFO = "movie";

    private AppDatabase mDb;
    private MovieAdapter mAdapter;
    private GridView gridView;

    MainViewModel viewModel;

    private Spinner spinner;
    final String[] choices = {"Most Popular", "Highest Rating", "Favorites"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,
//                android.R.layout.simple_dropdown_item_1line,
//                choices);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.choice_array, R.layout.spinner_style);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        int position = Utility.getSortOrderPosition(this,
                Utility.getSortOrder(this));
        spinner.setSelection(position, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                 //avoid onItemSelected calls during initialization
                    String selected = (String) spinner.getSelectedItem();
                    Log.d(TAG, "onItemClick: spinner selected item = " + selected);
                    Utility.setSortOrder(getApplicationContext(), selected);

                    //run query to update the gridview by sort order
                    viewModel.setMovies(null);
                    viewModel.getMovies();
                }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        gridView = (GridView)findViewById(R.id.grid_view_movies);
        mAdapter = new MovieAdapter(this,
                R.layout.item_grid_layout);

        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MovieEntry movie = mAdapter.getItem(position);
                Log.d(TAG, "onItemClick: id: " + movie.getId() +
                            " \nname: " + movie.getTitle());
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(MOVIE_INFO, movie);
                startActivity(intent);
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();

    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {
                Log.d(TAG, "onChanged: Updating list of movies from LiveData in ViewModel");
              mAdapter.setMovies(movieEntries);
            }
        });
    }


}
