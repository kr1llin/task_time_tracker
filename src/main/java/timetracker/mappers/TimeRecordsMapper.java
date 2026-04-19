package timetracker.mappers;

import org.apache.ibatis.annotations.*;
import timetracker.models.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TimeRecordsMapper {
    @Insert("INSERT INTO time_records (employee_id, task_id, begin_time, end_time, work_description) " +
            "VALUES (#{employeeId}, #{taskId}, #{beginTime}), #{endTime}, #{description}")
    @Options(useGeneratedKeys = true, keyColumn = "time_record_id", keyProperty = "id")
    int insert(TimeRecord timeRecord);

    @Select("SELECT time_record_id, employee_id, task_id, begin_time, end_time, work_description FROM time_records " +
            "WHERE time_record_id = #{employeeId} AND begin_time >= #{beginPeriod} AND end_time <= #{endPeriod}")
    @Results(id = "timeRecordsResultMap", value = {
            @Result(property = "id", column = "task_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status")
    })
    List<TimeRecord> selectByEmployeeAndTimePeriod(@Param("employeeId") Integer employeeId, @Param("beginPeriod") LocalDateTime beginPeriod,
                                                            @Param("endPeriod") LocalDateTime endPeriod);
}
