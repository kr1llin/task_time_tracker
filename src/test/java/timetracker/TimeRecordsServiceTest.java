package timetracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import timetracker.mappers.TimeRecordsMapper;
import timetracker.models.TimeRecord;
import timetracker.services.TimeRecordsService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TimeRecordsServiceTest {
    @Mock
    private TimeRecordsMapper timeRecordsMapper;

    @InjectMocks
    private TimeRecordsService timeRecordsService;

    @Test
    void testRecordCreationShouldReturnRecord(){
        TimeRecord record = new TimeRecord();
        record.setTaskId(1);
        record.setEmployeeId(100);
        record.setBeginTime(LocalDateTime.now().minusHours(5));
        record.setEndTime(LocalDateTime.now());
        record.setDescription("Test description");

        // 1 - successful insertion
        doReturn(1).when(timeRecordsMapper).insert(record);

        TimeRecord result = timeRecordsService.createTimeRecord(record);

        assertNotNull(result);
        assertEquals(1, result.getTaskId());
        assertEquals(1, result.getEmployeeId());
        verify(timeRecordsMapper, times(1)).insert(record);
    }

    @Test
    void testRecordsByEmployeeAndPeriodShouldReturnList(){
        Integer employeeId = 100;
        LocalDateTime start = LocalDateTime.of(2026,4,1,0,0);
        LocalDateTime end = LocalDateTime.of(2026,4,18,23,59);

        TimeRecord record1 = new TimeRecord();
        TimeRecord record2 = new TimeRecord();

        record1.setId(1);
        record2.setId(2);

        List<TimeRecord> expected = Arrays.asList(record1,record2);
        when(timeRecordsMapper.selectByEmployeeAndTimePeriod(employeeId, start, end)).thenReturn(expected);

        List<TimeRecord> actual = timeRecordsService.getByEmployeeAndTimePeriod(employeeId, start,end);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        verify(timeRecordsMapper).selectByEmployeeAndTimePeriod(employeeId, start,end);
    }

    @Test
    void testRecordsByEmployeeAndPeriod_WhenNoRecords_ShouldReturnEmptyList(){
        when(timeRecordsMapper.selectByEmployeeAndTimePeriod(any(), any(), any())).thenReturn(List.of());

        List<TimeRecord> actual = timeRecordsService.getByEmployeeAndTimePeriod(666, LocalDateTime.MIN, LocalDateTime.MIN);

        assertTrue(actual.isEmpty());
        verify(timeRecordsMapper).selectByEmployeeAndTimePeriod(any(), any(),any());
    }
}
