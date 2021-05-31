package com.gkmit.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //db name
    private static final String DB_NAME = "ToDoApp.db";

    //table names
    static final String USER_TABLE = "USER";
    static final String TODO_TABLE = "TODO";

    //user table columns
    static final String COLUMN_USER_NAME= "user_name";
    static final String COLUMN_USER_ID = "user_id";

    //task table columns
    static String COLUMN_TODO_TASK = "todo_task";
    static final String COLUMN_TODO_ID ="todo_task_id";

    //user table create query
    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + " ( COLUMN_USER_ID INTEGER PRIMARY KEY " +
            "AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT NOT NULL)";

    //todo table create query
    private String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + " ( COLUMN_TASK_ID  INTEGER PRIMARY KEY" +
            " AUTOINCREMENT, " + COLUMN_TODO_TASK + " TEXT NOT NULL UNIQUE,"
            + COLUMN_USER_ID + " INTEGER NOT NULL, FOREIGN KEY (" +
            COLUMN_USER_ID + ") REFERENCES " + USER_TABLE +  " (" + COLUMN_USER_ID + "));";

    //drop table queries
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE;
    private String DROP_TODO_TABLE = "DROP TABLE IF EXISTS " + TODO_TABLE;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        sqlDB.execSQL(CREATE_USER_TABLE);
        sqlDB.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
        sqlDB.execSQL(DROP_USER_TABLE);
        sqlDB.execSQL(DROP_TODO_TABLE);
        onCreate(sqlDB);
    }
}
