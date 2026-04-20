package timetracker.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import timetracker.models.TimeRecord;
import timetracker.models.dto.ErrorResponse;
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

    @Tag(name = "post", description = "POST methods of TimeTracker API")
    @Operation(summary = "Post new time record",
            description = "Create time record. The response is created TimeRecord object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Time record created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeRecordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task or employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
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

    @Tag(name = "get", description = "GET methods of TimeTracker API")
    @Operation(summary = "Get time records by employee and time period",
            description = "Get time records of employee between period. The response is list of TimeRecord objects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time records found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TimeRecordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TimeRecordResponse> getTimeRecordsByEmployeeAndPeriod(@Parameter(description = "ID of employee to be retrieved",
                                                                                  required = true) @RequestParam Integer employeeId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime){
        List<TimeRecord> timeRecords = timeRecordsService.getByEmployeeAndTimePeriod(employeeId, beginTime, endTime);
        return timeRecords.stream().map(TimeRecordResponse::toResponse).collect(Collectors.toList());
    }
}
