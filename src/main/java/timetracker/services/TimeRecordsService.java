package timetracker.services;

import org.springframework.stereotype.Service;
import timetracker.exceptions.TaskNotFoundException;
import timetracker.exceptions.TimeRecordNotFoundException;
import timetracker.mappers.TaskMapper;
import timetracker.mappers.TimeRecordsMapper;
import timetracker.models.Task;
import timetracker.models.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeRecordsService {
    private final TimeRecordsMapper timeRecordsMapper;
    private final TaskMapper taskMapper;

    public TimeRecordsService(TimeRecordsMapper timeRecordsMapper, TaskMapper taskMapper){
        this.timeRecordsMapper = timeRecordsMapper;
        this.taskMapper = taskMapper;
    }

    public TimeRecord createTimeRecord(TimeRecord timeRecord){
        // check if task exists
        Task task = taskMapper.selectById(timeRecord.getTaskId());
        if (task == null) {
            throw new TaskNotFoundException(timeRecord.getTaskId());
        }
        timeRecordsMapper.insert(timeRecord); // gonna set id
        return timeRecord;
    }

    public List<TimeRecord> getByEmployeeAndTimePeriod(Integer employeeId, LocalDateTime beginPeriod, LocalDateTime endPeriod){
        return timeRecordsMapper.selectByEmployeeAndTimePeriod(employeeId, beginPeriod, endPeriod);
    }
}

