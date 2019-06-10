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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddThh:mm:ssZ");
            return new java.sql.Date(format.parse(date).getTime());
        }
        catch (Exception exception) {
            return null;
        }
    }

}
