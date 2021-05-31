package com.gkmit.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //db name
    private static final String DB_NAME = "ToDoApp.db";

    //user table
    static final String USER_TABLE = "USER";

    //user table columns
    static final String COLUMN_USER_NAME= "user_name";
    static final String COLUMN_USER_ID = "user_id";

    //user table create query
    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + " ( COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_NAME + " TEXT NOT NULL)";
    //drop user table query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        sqlDB.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
        sqlDB.execSQL(DROP_USER_TABLE);
        onCreate(sqlDB);
    }
}
