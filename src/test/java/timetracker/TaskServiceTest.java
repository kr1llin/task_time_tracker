package timetracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import timetracker.exceptions.TaskNotFoundException;
import timetracker.mappers.TaskMapper;
import timetracker.models.Task;
import timetracker.services.TaskService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask_ShouldSetStatusNew(){
        Task task = new Task();
        task.setTitle("TestTask");
        task.setDescription("Desc");

        when(taskMapper.insert(task)).thenReturn(1);

        Task actual = taskService.createTask(task);

        assertNotNull(actual);
        assertEquals("NEW", actual.getStatus());
        assertEquals("TestTask", actual.getTitle());
        assertEquals("Desc", actual.getDescription());
        verify(taskMapper, times(1)).insert(task);
    }

    @Test
    void testGetTaskWhenExists_ShouldReturnTask(){
        Task task = new Task();
        task.setId(1);
        task.setTitle("Old task");
        task.setStatus("IN_PROGRESS");

        when(taskMapper.selectById(1)).thenReturn(task);

        Task actual = taskService.getTaskById(1);

        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Old task", task.getDescription());
        verify(taskMapper, times(1)).selectById(1);
    }

    @Test
    void testGetTaskWhenNotExists_ShouldThrowException(){
        when(taskMapper.selectById(1)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1));
        verify(taskMapper, times(1)).selectById(1);
    }

    @Test
    void testUpdateTaskWhenExists_ShouldUpdateAndReturnTask(){
        Task task = new Task();
        task.setId(1);
        task.setStatus("NEW");

        when(taskMapper.selectById(1)).thenReturn(task);
        when(taskMapper.updateStatus(1, "DONE")).thenReturn(1);

        Task actual = taskService.updateStatusById(1, "DONE");

        assertEquals("DONE", actual.getStatus());
        verify(taskMapper).selectById(1);
        verify(taskMapper).updateStatus(1, "DONE");
    }

    @Test
    void testUpdateTaskWhenNotExists_ShouldThrowException(){
        when(taskMapper.selectById(1)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> taskService.updateStatusById(1, "DONE"));
        verify(taskMapper, never()).updateStatus(any(), any());
    }
}
