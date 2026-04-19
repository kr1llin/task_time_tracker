package timetracker.exceptions;

import java.time.LocalDateTime;

public class TimeRecordNotFoundException extends RuntimeException {
    public TimeRecordNotFoundException(Integer emploeeId, LocalDateTime begin, LocalDateTime end) {
        super("TaskRecord for employee #" + emploeeId +
                " and time period [" + begin + "; " + end + "] not found");}
}
