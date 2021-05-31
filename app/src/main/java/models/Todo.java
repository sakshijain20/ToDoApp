public class Todo {
    private int todo_id;
    private String task;

    public Todo(){}
    public Todo(int todo_id, String task) {
        this.todo_id = todo_id;
        this.task = task;
    }

    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
