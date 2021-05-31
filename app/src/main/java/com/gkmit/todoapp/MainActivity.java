package com.gkmit.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        logInUser();
    }

    private void logInUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Log In");
        builder.setMessage("Please enter your username..");
        final EditText uname = new EditText(this);
        builder.setView(uname);

        builder.setPositiveButton("Log In", (dialogInterface, i) -> {
            String username = uname.getText().toString();

            if(DBHelper.USER_TABLE.contains(username)){
                //user will get logged in
            }
            else{
                sqLiteDatabase = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.clear();
                values.put(DBHelper.COLUMN_USER_NAME, username);
                sqLiteDatabase.insertWithOnConflict(DBHelper.USER_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    //initializing objects
    private void initObjects() {
        dbHelper = new DBHelper(MainActivity.this);
    }
}