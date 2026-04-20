package timetracker.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import timetracker.models.Task;
import timetracker.models.dto.ErrorResponse;
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

    @Tag(name = "post", description = "POST methods of TimeTracker API")
    @Operation(summary = "Post new task",
            description = "Create task. The response is created Task object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input (validation failed)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody TaskRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        return new TaskResponse(taskService.createTask(task));
    }

    @Tag(name = "get", description = "GET methods of TimeTracker API")
    @Operation(summary = "Get task by id",
            description = "Get task. The response is Task object of specified id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID format",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@Parameter(description = "ID of task to be retrieved", required = true) @PathVariable Integer id){
        return new TaskResponse(taskService.getTaskById(id));
    }

    @Tag(name = "patch", description = "PATCH methods of TimeTracker API")
    @Operation(summary = "Update task status by id",
            description = "Update task status. The response is updated Task object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status value or validation error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse updateStatus(@Parameter(description = "ID of task to be retrieved", required = true) @PathVariable Integer id, @Valid @RequestBody UpdateTaskStatusRequest request){
        return new TaskResponse(taskService.updateStatusById(id, request.getStatus()));
    }
}
