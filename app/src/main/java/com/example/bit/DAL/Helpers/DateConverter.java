package com.example.bit.DAL.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static Date fromString(String date) {
        try {
            date = date.replace("Z", "+00:00");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return new java.sql.Date(format.parse(date).getDate());
        }
        catch (Exception exception) {
            return null;
        }
    }

}
