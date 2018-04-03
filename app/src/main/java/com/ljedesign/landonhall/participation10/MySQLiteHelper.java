package com.ljedesign.landonhall.participation10;

/* This class is a helper class that helps to open a database connection and create a database. The creation of the database will be executed in the onCreate Method.*/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "comments"; //Table Name
    public static final String COLUMN_ID = "_id"; //ID Column
    public static final String COLUMN_COMMENT = "comment"; //Comment Column
    public static final String COLUMN_RATING = "rating";

    private static final String DATABASE_NAME = "commments.db"; //Database Name
    private static final int DATABASE_VERSION = 2; //Database Version

    /*SQL: CREATE TABLE comments(
            _id integer primary key autoincrement,
            comment text not null
            );*/
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null, " + COLUMN_RATING + " integer);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

}