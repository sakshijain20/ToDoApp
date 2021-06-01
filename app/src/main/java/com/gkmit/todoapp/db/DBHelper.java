package com.gkmit.todoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gkmit.todoapp.models.Todo;
import com.gkmit.todoapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    //db name
    private static final String DB_NAME = "ToDoApp.db";
    //table names
    public static final String USER_TABLE = "USER";
    public static final String TODO_TABLE = "TODO";

    //user table columns
    public static final String COLUMN_USER_NAME= "user_name";
    public static final String COLUMN_USER_ID = "user_id";

    //task table columns
    public static String COLUMN_TODO_TASK = "todo_task";
    static final String COLUMN_TODO_ID ="todo_task_id";

    //user table create query
    private final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + " ( " + COLUMN_USER_ID  + " " +
            "INTEGER PRIMARY KEY " + "AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT NOT NULL UNIQUE )";

    //todo table create query
    private final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + " ( COLUMN_TASK_ID  INTEGER " +
            "PRIMARY KEY " + " AUTOINCREMENT, " + COLUMN_TODO_TASK + " TEXT NOT NULL,"
            + COLUMN_USER_ID + " INTEGER NOT NULL , FOREIGN KEY (" +
            COLUMN_USER_ID + ") REFERENCES " + USER_TABLE +  " (" + COLUMN_USER_ID + "));";

    private SQLiteDatabase db;

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
        //drop table queries
        sqlDB.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(sqlDB);
    }

    public void addUser(String username){
        User user = new User();
        user.setName(username);
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        db.insert(USER_TABLE, null, values);
    }

    public void addTask(Todo task) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_TASK, task.getTask());
        values.put(COLUMN_USER_ID,task.getUserId());
        db.insert(TODO_TABLE, null, values);
    }

    public List<Todo> getUserTasks(int userId){
        List<Todo> todoList = new ArrayList<>();
        db  = this.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TODO_TABLE, new String[] {DBHelper.COLUMN_TODO_TASK},
                DBHelper.COLUMN_USER_ID + " =?", new String[] {String.valueOf(userId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            while(cursor.moveToNext()){
                Todo todo = new Todo();
                todo.setTask(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TODO_TASK)));
                todoList.add(todo);
            }
        }
        cursor.close();
        return todoList;
    }

}
