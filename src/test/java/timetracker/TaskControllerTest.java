package timetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import timetracker.controllers.TaskController;
import timetracker.exceptions.TaskNotFoundException;
import timetracker.models.Task;
import timetracker.models.dto.TaskRequest;
import timetracker.models.dto.UpdateTaskStatusRequest;
import timetracker.services.TaskService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask_ShouldReturnCreatedTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("Task title");
        request.setDescription("Desc");

        Task createdTask = new Task();
        createdTask.setId(1);
        createdTask.setTitle("Task title");
        createdTask.setDescription("Desc");
        createdTask.setStatus("NEW");

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task title"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.description").value("Desc"));
    }

    @Test
    void testGetTaskWhenExists_ShouldReturnTask() throws Exception {
        Task task = new Task();
        task.setId(5);
        task.setTitle("Task title");
        task.setStatus("IN_PROGRESS");

        when(taskService.getTaskById(5)).thenReturn(task);

        mockMvc.perform(get("/tasks/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.title").value("Task title"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void testGetTaskWhenNotFound_ShouldReturn404() throws Exception {
        when(taskService.getTaskById(5)).thenThrow(new TaskNotFoundException(5));

        mockMvc.perform(get("/tasks/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TASK_NOT_FOUND"));
    }

    @Test
    void testUpdateStatus_WithValidStatus_ShouldReturnUpdatedTask() throws Exception {
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus("DONE");

        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setStatus("DONE");

        when(taskService.updateStatusById(1, "DONE")).thenReturn(updatedTask);

        mockMvc.perform(patch("/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void testUpdateStatus_WithInvalidStatus_ShouldReturn400() throws Exception {
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus("INVALID");

        mockMvc.perform(patch("/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"));
    }

    @Test
    void testCreateTask_WithBlankName_ShouldReturn400() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
