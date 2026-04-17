package timetracker.exceptions;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(Integer id) {
    super("Task #" + id + " not found");
  }
}
