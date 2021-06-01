package com.gkmit.todoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gkmit.todoapp.Adapter;
import com.gkmit.todoapp.R;
import com.gkmit.todoapp.db.*;
import com.gkmit.todoapp.models.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private List<Todo> todoList;
    TextView tvUserName;
    LinearLayoutManager layoutManager;
    Todo task;
    User user;

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
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(DBHelper.USER_TABLE,
                        new String[]{DBHelper.COLUMN_USER_NAME},
                        null, null, null, null, null);

                if(cursor.moveToFirst()){
                    while(cursor.moveToNext())
                        if(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_NAME)).equalsIgnoreCase(username)){
                           user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_ID)));
                           user.setName(username);
                        }
                }

            }
            else{
                dbHelper.addUser(username);
                user.setName(username);
            }
            tvUserName.setText("Hello " + user.getName());
            updateUI();
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
            dbHelper = new DBHelper(MainActivity.this);

            builder.setPositiveButton("Add Task", (dialogInterface, i) -> {
                String todoTaskInput = todoET.getText().toString();
                task.setUserId(user.getId());
                task.setTask(todoTaskInput);
                dbHelper.addTask(task);
                updateUI();
            });

            builder.setNegativeButton("Cancel", null);

            builder.create().show();
        }
        return true;
    }

     void updateUI() {
        todoList.clear();
        todoList.addAll(dbHelper.getUserTasks(user.getId()));
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
        tvUserName = findViewById(R.id.textview_username);
        todoList=new ArrayList<>();
        layoutManager=new LinearLayoutManager(MainActivity.this);
        task = new Todo();
        user = new User();
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView textViewToDo =  v.findViewById(R.id.title);
        String taskItem = textViewToDo.getText().toString();

        String deleteTodoItemSql = "DELETE FROM " + dbHelper.TODO_TABLE +
                " WHERE " + dbHelper.COLUMN_TODO_TASK + " = '" + taskItem + "'";

        dbHelper = new DBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL(deleteTodoItemSql);
        updateUI();
    }

}