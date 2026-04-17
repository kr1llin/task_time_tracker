package timetracker.models.dto;

import timetracker.models.Task;

public class TaskResponse {
    private Integer id;
    private String title;
    private String description;
    private String status;

    public TaskResponse(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
