package timetracker.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import timetracker.models.Task;
import timetracker.models.dto.TaskRequest;
import timetracker.models.dto.TaskResponse;
import timetracker.models.dto.UpdateTaskStatusRequest;
import timetracker.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody TaskRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Integer id){
        return new TaskResponse(taskService.getTaskById(id));
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable Integer id, @Valid @RequestBody UpdateTaskStatusRequest request){
        return new TaskResponse(taskService.updateStatusById(id, request.getStatus()));
    }
}
