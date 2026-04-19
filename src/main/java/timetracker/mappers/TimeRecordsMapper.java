package timetracker.mappers;

import org.apache.ibatis.annotations.*;
import timetracker.models.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TimeRecordsMapper {
    @Insert("INSERT INTO time_records (employee_id, task_id, begin_time, end_time, work_description) " +
            "VALUES (#{employeeId}, #{taskId}, #{beginTime}, #{endTime}, #{description})")
    @Options(useGeneratedKeys = true, keyColumn = "time_record_id", keyProperty = "id")
    int insert(TimeRecord timeRecord);

    @Select("SELECT time_record_id, employee_id, task_id, begin_time, end_time, work_description FROM time_records " +
            "WHERE employee_id = #{employeeId} AND begin_time >= #{beginPeriod} AND end_time <= #{endPeriod}")
    @Results(id = "timeRecordsResultMap", value = {
            @Result(property = "id", column = "time_record_id"),
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "beginTime", column = "begin_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "description", column = "work_description")
    })
    List<TimeRecord> selectByEmployeeAndTimePeriod(@Param("employeeId") Integer employeeId, @Param("beginPeriod") LocalDateTime beginPeriod,
                                                            @Param("endPeriod") LocalDateTime endPeriod);
}
