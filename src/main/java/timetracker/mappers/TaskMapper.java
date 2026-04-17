package timetracker.mappers;


import org.apache.ibatis.annotations.*;
import timetracker.models.Task;

@Mapper
public interface TaskMapper {
    @Insert("INSERT INTO tasks (title, description, status) VALUES (#{title}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "task_id", keyProperty = "id")
    int insert(Task task);

    @Select("SELECT task_id, title, description FROM tasks WHERE id = #{id}")
    @Results(id = "taskResultMap", value = {
            @Result(property = "id", column = "task_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status")
    })
    Task selectById(Integer id);

    @Update("UPDATE tasks SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
}
