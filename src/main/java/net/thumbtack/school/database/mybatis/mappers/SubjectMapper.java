package net.thumbtack.school.database.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.thumbtack.school.database.model.Subject;

public interface SubjectMapper {
    /*
     * Subject всегда вставляется независимо от чего бы то ни было. В дальнейшем Subject
        может быть привязан к одной или нескольким Group.
     */
    @Insert("INSERT INTO subject SET name = #{name}")
    @Options(useGeneratedKeys = true)
    Integer insert(Subject subject);

    @Select("SELECT * FROM subject WHERE id = #{id}")
    Subject getById(int id);

    @Select("SELECT * FROM subject")
    List<Subject> getAll();

    @Update("UPDATE subject SET name = #{name} WHERE id = #{id}")
    void update(Subject subject);

    @Delete("DELETE FROM subject WHERE id = #{id}")
    void delete(Subject subject);

    @Delete("DELETE FROM subject")
    void deleteAll();

    @Select("SELECT subject.id, name FROM subject " +
            "JOIN subjectGroup ON groupId = #{id} AND subjectId = subject.id")
    List<Subject> getByGroupId(int id);
}
