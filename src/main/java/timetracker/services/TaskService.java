package timetracker.services;

import org.springframework.stereotype.Service;
import timetracker.exceptions.TaskNotFoundException;
import timetracker.mappers.TaskMapper;
import timetracker.models.Task;

@Service
public class TaskService {
    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }

    public Task createTask(Task task){
        task.setStatus("NEW");
        taskMapper.insert(task); // gonna set id
        return task;
    }

    public Task getTaskById(Integer id){
        Task task = taskMapper.selectById(id);
        if (task == null) throw new TaskNotFoundException(id);
        return task;
    }

    public Task updateStatusById(Integer id, String status){
        Task task = getTaskById(id);
        taskMapper.updateStatus(id, status);
        task.setStatus(status);
        return task;
    }
}
