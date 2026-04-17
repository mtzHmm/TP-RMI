package interfaces;

import java.io.Serializable;

public class TaskData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private int priority;

    public TaskData() {
    }

    public TaskData(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}
