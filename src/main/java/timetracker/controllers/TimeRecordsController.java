package timetracker.controllers;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import timetracker.models.TimeRecord;
import timetracker.models.dto.TimeRecordRequest;
import timetracker.models.dto.TimeRecordResponse;
import timetracker.services.TimeRecordsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/time-records")
public class TimeRecordsController {
    private final TimeRecordsService timeRecordsService;

    public TimeRecordsController(TimeRecordsService timeRecordsService){
        this.timeRecordsService = timeRecordsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeRecordResponse createTimeRecord(@Valid @RequestBody TimeRecordRequest taskRequest){
        TimeRecord timeRecord = new TimeRecord();

        timeRecord.setEmployeeId(taskRequest.getEmployeeId());
        timeRecord.setTaskId(taskRequest.getTaskId());
        timeRecord.setBeginTime(taskRequest.getBeginTime());
        timeRecord.setEndTime(taskRequest.getEndTime());
        timeRecord.setDescription(taskRequest.getDescription());

        return TimeRecordResponse.toResponse(timeRecordsService.createTimeRecord(timeRecord));
    }

    @GetMapping
    public List<TimeRecordResponse> getTimeRecordsByEmployeeAndPeriod(@RequestParam Integer employeeId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end){
        List<TimeRecord> timeRecords = timeRecordsService.getByEmployeeAndTimePeriod(employeeId, begin, end);
        return timeRecords.stream().map(TimeRecordResponse::toResponse).collect(Collectors.toList());
    }
}
