package timetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import timetracker.controllers.TimeRecordsController;
import timetracker.models.TimeRecord;
import timetracker.models.dto.TimeRecordRequest;
import timetracker.services.TimeRecordsService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeRecordsController.class)
class TimeRecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TimeRecordsService timeRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTimeRecord_ShouldReturnCreatedRecord() throws Exception {
        TimeRecordRequest request = new TimeRecordRequest();
        request.setEmployeeId(1);
        request.setTaskId(1);
        request.setBeginTime(LocalDateTime.of(2025, 4, 17, 9, 0));
        request.setEndTime(LocalDateTime.of(2025, 4, 17, 13, 0));
        request.setDescription("Desc");

        TimeRecord saved = new TimeRecord();
        saved.setId(1);
        saved.setEmployeeId(1);
        saved.setTaskId(5);
        saved.setBeginTime(request.getBeginTime());
        saved.setEndTime(request.getEndTime());
        saved.setDescription("Desc");

        when(timeRecordService.createTimeRecord(any(TimeRecord.class))).thenReturn(saved);

        mockMvc.perform(post("/time-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.description").value("Desc"));
    }

    @Test
    void testGetTimeRecord_ShouldReturnList() throws Exception {
        Integer employeeId = 1;
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 18, 23, 59);

        TimeRecord record = new TimeRecord();
        record.setId(1);
        record.setEmployeeId(employeeId);
        record.setTaskId(1);
        record.setBeginTime(start);
        record.setEndTime(end);
        record.setDescription("Desc");

        when(timeRecordService.getByEmployeeAndTimePeriod(eq(employeeId), any(), any()))
                .thenReturn(List.of(record));

        mockMvc.perform(get("/time-records")
                        .param("employeeId", "101")
                        .param("start", "2026-04-01T00:00:00")
                        .param("end", "2026-04-18T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }

    @Test
    void testCreateTimeRecord_WithMissingEmployeeId_ShouldReturn400() throws Exception {
        TimeRecordRequest request = new TimeRecordRequest();
        request.setTaskId(1);
        request.setBeginTime(LocalDateTime.now());
        request.setEndTime(LocalDateTime.now().plusHours(1));

        mockMvc.perform(post("/time-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetTimeRecord_WithInvalidDateFormat_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/time-records")
                        .param("employeeId", "101")
                        .param("start", "2026/04/01")
                        .param("end", "2026/04/18"))
                .andExpect(status().isBadRequest());
    }
}