package net.thumbtack.school.database.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;

public interface GroupMapper {

    //Group не может быть вставлена без привязки к School
    @Insert("INSERT INTO `group` ( schoolId, name, room) "
            + "VALUES ( #{school.id}, #{group.name}, #{group.room} )")
    @Options(useGeneratedKeys = true, keyProperty = "group.id")
    void insert(@Param("school") School school, @Param("group") Group group);

    @Update("UPDATE `group` SET name = #{name}, room = #{room} WHERE id = #{id} ")
    void update(Group group);

    @Select("SELECT * FROM `group`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroupId",
                            fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroupId",
                            fetchType = FetchType.LAZY))
    })
    List<Group> getAll();

    @Delete("DELETE FROM `group` WHERE id = #{id}")
    void delete(Group group);

    @Update("UPDATE trainee SET groupId = #{group.id} WHERE id = #{trainee.id}")
    void moveTraineeToGroup(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Update("UPDATE `trainee` SET groupId = NULL WHERE id = #{id} ")
    void deleteTraineeFromGroup(Trainee trainee);

    @Insert("INSERT INTO subjectGroup (groupId, subjectId) VALUES (#{group.id}, #{subject.id})")
    void addSubjectToGroup(@Param("group") Group group, @Param("subject") Subject subject);

    @Select("SELECT * FROM `group` WHERE schoolId = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroupId",
                            fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroupId",
                            fetchType = FetchType.LAZY))
    })
    Group getBySchoolId(int id);

}
