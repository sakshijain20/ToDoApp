package com.gkmit.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.gkmit.todoapp.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private List<Todo> todoList;
    LinearLayoutManager layoutManager;
    Todo task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        logInUser();
        updateUI();
    }

    private void logInUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Log In");
        builder.setMessage("Please enter your username..");
        final EditText uname = new EditText(this);
        builder.setView(uname);

        builder.setPositiveButton("Log In", (dialogInterface, i) -> {
            String username = uname.getText().toString();

            if(DBHelper.COLUMN_USER_NAME.contains(username)){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add Todo Task Item");
            builder.setMessage("Please write the task...");
            final EditText todoET = new EditText(this);
            builder.setView(todoET);

            builder.setPositiveButton("Add Task", (dialogInterface, i) -> {
                String todoTaskInput = todoET.getText().toString();

                todoList.add(new Todo(todoTaskInput));
                task.setTask(todoTaskInput);
                dbHelper.addTask(task);

                dbHelper = new DBHelper(MainActivity.this);
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.clear();

                values.put(dbHelper.COLUMN_TODO_TASK, todoTaskInput);
                sqLiteDatabase.insertWithOnConflict(dbHelper.TODO_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                updateUI();
            });

            builder.setNegativeButton("Cancel", null);

            builder.create().show();
        }
        return true;
    }

    private void updateUI() {
        Adapter adapter = new Adapter(todoList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter.notifyDataSetChanged();
    }


    //initializing objects
    private void initObjects() {
        dbHelper = new DBHelper(MainActivity.this);
        recyclerView=findViewById(R.id.recycler_view);
        todoList=new ArrayList<>();
        layoutManager=new LinearLayoutManager(MainActivity.this);
        task = new Todo();
    }



}