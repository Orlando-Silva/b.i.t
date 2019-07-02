package com.app.bit.DAL.Helpers;

import com.app.bit.DAL.Enums.Status;

import androidx.room.TypeConverter;

public class EnumConverter {

    @TypeConverter
    public static Status toStatus(int status){
        return Status.values()[status];
    }

    @TypeConverter
    public static int toInt(Status status){
        return status.getVALUE();
    }

}
