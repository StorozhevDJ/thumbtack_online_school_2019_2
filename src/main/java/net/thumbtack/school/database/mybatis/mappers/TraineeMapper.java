package net.thumbtack.school.database.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;
import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;


public interface TraineeMapper {
    /*
     * Trainee может как принадлежать, так и не принадлежать Group. Поэтому возможна
    вставка Trainee без указания Group, привязка к Group может быть произведена позже.
     */
    @Insert("INSERT INTO trainee (groupId, firstName, lastName, rating) VALUES "
            + "( #{group.id}, #{trainee.firstName}, #{trainee.lastName}, #{trainee.rating} )")
    @Options(useGeneratedKeys = true, keyProperty = "trainee.id")
    Integer insert(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Select("SELECT * FROM trainee WHERE id = #{id}")
    Trainee getById(int id);

    @Select("SELECT * FROM trainee")
    List<Trainee> getAll();

    @Update("UPDATE trainee "
            + "SET firstName=#{firstName}, lastName=#{lastName}, rating = #{rating} "
            + "WHERE id=#{id}")
    void update(Trainee trainee);

    @Select({"<script>",
            "SELECT * FROM trainee",
            "<where>",
            "<if test='firstName != null'> firstName like #{firstName}",
            "</if>",
            "<if test='lastName != null'> AND lastName like #{lastName}",
            "</if>",
            "<if test='rating != null'> AND rating = #{rating}",
            "</if>",
            "</where>",
            "</script>"})
    List<Trainee> getAllWithParams(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("rating") Integer rating);

    @Insert({"<script>",
            "INSERT INTO trainee (firstName, lastName, rating) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item.firstName}, #{item.lastName}, #{item.rating} )",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsert(@Param("list") List<Trainee> trainees);

    @Delete("DELETE FROM trainee WHERE id = #{id}")
    void delete(Trainee trainee);

    @Delete("DELETE FROM trainee")
    void deleteAll();

    @Select("SELECT * FROM trainee WHERE groupId = #{id}")
    Trainee getByGroupId(int id);
}
