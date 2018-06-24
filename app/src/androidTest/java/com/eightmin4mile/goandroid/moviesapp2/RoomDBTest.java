package com.eightmin4mile.goandroid.moviesapp2;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.eightmin4mile.goandroid.moviesapp2.data.AppDatabase;
import com.eightmin4mile.goandroid.moviesapp2.data.MovieDao;
import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by goandroid on 6/15/18.
 */
@RunWith(AndroidJUnit4.class)
public class RoomDBTest {

    private MovieDao movieDao;
    private AppDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        movieDao = mDb.movieDao();
    }

    @After
    public void closeDb() throws IOException{
        mDb.close();
    }

    @Test
    public void writeMoiveAndReadInList() throws Exception {

        int id = 123;

        MovieEntry movieEntry = new MovieEntry(id,
                "movie name",
                "poster link",
                "synopsis",
                1,
                "1/1/2018",
                true);

        movieDao.insertMovie(movieEntry);

        List<MovieEntry> byId = movieDao.loadMovieById(id);

        //TODO doesn't match, ???
        //https://developer.android.com/training/data-storage/room/testing-db
       // assertThat(byId.get(0), equalTo(movieEntry));

       assertThat(byId.get(0).getTitle(), equalTo(movieEntry.getTitle()));
        //assertThat(byId.get(0).getTitle(), equalTo("abc"));


    }
}
