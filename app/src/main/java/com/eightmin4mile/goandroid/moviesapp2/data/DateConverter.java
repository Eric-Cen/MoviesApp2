package com.eightmin4mile.goandroid.moviesapp2.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by goandroid on 6/13/18.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}