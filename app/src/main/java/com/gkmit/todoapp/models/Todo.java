package com.gkmit.todoapp.models;

public class Todo {
    private int todoId;
    private String task;
    private int userId;

    public Todo() {
    }

    public Todo(int todoId, String task, int userId) {
        this.todoId = todoId;
        this.task = task;
        this.userId = userId;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
