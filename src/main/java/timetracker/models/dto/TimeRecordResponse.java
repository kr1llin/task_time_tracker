package timetracker.models.dto;

import timetracker.models.TimeRecord;

import java.time.LocalDateTime;

public class TimeRecordResponse {
    private Integer id;
    private Integer employeeId;
    private Integer taskId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String description;

    static public TimeRecordResponse toResponse(TimeRecord timeRecord) {
        TimeRecordResponse newResp = new TimeRecordResponse();
        newResp.setId(timeRecord.getId());
        newResp.setEmployeeId(timeRecord.getEmployeeId());
        newResp.setTaskId(timeRecord.getTaskId());
        newResp.setBeginTime(timeRecord.getBeginTime());
        newResp.setEndTime(timeRecord.getEndTime());
        newResp.setDescription(timeRecord.getDescription());
        return newResp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
