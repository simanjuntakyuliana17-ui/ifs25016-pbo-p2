package domain.entity;

// id, title, finished
public class Todo {
    private final int id;

    private String title;
    private boolean finished;

    public Todo(int id, String title, boolean finished) {
        this.id = id;
        this.title = title;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
