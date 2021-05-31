package com.gkmit.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ToDoApp.db";
    public static final String USER_TABLE = "USER";
    public static final String USERCOL1= "name";
    public static final String _ID = BaseColumns._ID;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String createUserTable = "CREATE TABLE " + USER_TABLE + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERCOL1 + " TEXT NOT NULL)";
        sqlDB.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(sqlDB);
    }
}
