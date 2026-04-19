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

    public TimeRecordsService(TimeRecordsMapper timeRecordsMapper){
        this.timeRecordsMapper = timeRecordsMapper;
    }

    public TimeRecord createTimeRecord(TimeRecord timeRecord){
        timeRecordsMapper.insert(timeRecord); // gonna set id
        return timeRecord;
    }

    public List<TimeRecord> getByEmployeeAndTimePeriod(Integer employeeId, LocalDateTime beginPeriod, LocalDateTime endPeriod){
        List<TimeRecord> timeRecords = timeRecordsMapper.selectByEmployeeAndTimePeriod(employeeId, beginPeriod, endPeriod);
        if (timeRecords == null) throw new TimeRecordNotFoundException(employeeId, beginPeriod, endPeriod);
        return timeRecords;
    }
}

