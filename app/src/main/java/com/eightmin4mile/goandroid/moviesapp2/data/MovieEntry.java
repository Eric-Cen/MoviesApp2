package com.eightmin4mile.goandroid.moviesapp2.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.Date;

/**
 * Created by goandroid on 6/1/18.
 */

@Entity(tableName = "movie")
public class MovieEntry implements Parcelable{

    // the titles and IDs of the user's favorite movies
    // to store movie poster, synopsis, user rating and release date

    @PrimaryKey(autoGenerate = false)
    private long id;

    private String title;
    private String poster;
    private String synopsis;
    private double rating;
    private int vote_count;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    private boolean favorites;

    public MovieEntry(long id,
                      String title,
                      String poster,
                      String synopsis,
                      double rating,
                      int vote_count,
                      String releaseDate,
                      boolean favorites){
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.synopsis = synopsis;
        this.rating = rating;
        this.vote_count = vote_count;
        this.releaseDate = releaseDate;
        this.favorites = favorites;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected MovieEntry(Parcel in){
        this.id = in.readLong();
        this.title = in.readString();
        this.poster = in.readString();
        this.synopsis = in.readString();
        this.rating  = in.readDouble();
        this.vote_count = in.readInt();
        this.releaseDate = in.readString();
        this.favorites = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(synopsis);
        dest.writeDouble(rating);
        dest.writeInt(vote_count);
        dest.writeString(releaseDate);
        dest.writeByte((byte) (favorites ? 1: 0));
    }

    public static final Creator<MovieEntry> CREATOR = new Creator<MovieEntry>() {
        @Override
        public MovieEntry createFromParcel(Parcel parcel) {
            return new MovieEntry(parcel);
        }

        @Override
        public MovieEntry[] newArray(int size) {
            return new MovieEntry[size];
        }
    };
}
