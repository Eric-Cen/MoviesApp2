package com.eightmin4mile.goandroid.moviesapp2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eightmin4mile.goandroid.moviesapp2.data.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goandroid on 6/12/18.
 */

public class MovieAdapter extends ArrayAdapter<MovieEntry>{
    private static final String TAG = "MovieAdapter";

    private Context mContext;
    private int layoutResourceId;
    private List<MovieEntry> movieData;

    public static final String baseImageURL = "http://image.tmdb.org/t/p/";

    public static final String thumbnailSize = "w185/";


    public MovieAdapter(Context context,
                        int layoutResourceId){
        super(context, layoutResourceId);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
    }

    public List<MovieEntry>getMovies() {return movieData;}

    public void setMovies(List<MovieEntry> movieEntries){
        movieData = movieEntries;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movieData == null ? 0 : movieData.size();
    }

    @Nullable
    @Override
    public MovieEntry getItem(int position) {
        return movieData.get(position);
    }

    static class ViewHolder {
        TextView movieTitle;
        ImageView movieImage;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        MovieEntry currentMovie = movieData.get(position);
        //Log.d(TAG, "getView: at item #" + position);

        if(convertView == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.movieTitle = (TextView)convertView.findViewById(R.id.tv_movie_name);
            holder.movieImage = (ImageView) convertView.findViewById(R.id.iv_movie_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.movieTitle.setText(currentMovie.getTitle());

        if(currentMovie.isFavorites()){
            holder.movieTitle.setTextColor(Color.RED);
        } else {
            holder.movieTitle.setTextColor(Color.GRAY);
        }

        String stringPath = baseImageURL + thumbnailSize + movieData.get(position).getPoster();
        Picasso.with(mContext)
                .load(stringPath)
                .error(R.drawable.image_not_available)
                .into(holder.movieImage);
        return convertView;
    }
}
