package com.example.bit.DAL.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper  {

    public static final String DATABASE_NAME = "BIT.db";
    public static final int DATABASE_VERSION = 1;

    private Schema schema;

    public DataHelper(Context context, Schema schema) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.schema = schema;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(schema.getCreateTableCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(schema.getDropTableCommand());
        onCreate(db);

    }
}
