package net.thumbtack.school.database.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import net.thumbtack.school.database.model.School;

public interface SchoolMapper {

    /*
     * School вставляется без своих Group, добавление в нее Group должно быть
     * сделано позже.
     */
    @Insert("INSERT INTO school (id, name, year) VALUES " + "( #{id}, #{name}, #{year} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(School school);

    @Select("SELECT * FROM school WHERE id = #{id}")
    @Results({@Result(property = "id", column = "id"),
            @Result(property = "groups", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.GroupMapper.getBySchoolId",
                            fetchType = FetchType.LAZY))})
    School getById(int id);

    @Select("SELECT * FROM school")
    @Results({@Result(property = "id", column = "id"),
            @Result(property = "groups", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.GroupMapper.getBySchoolId",
                            fetchType = FetchType.LAZY))})
    List<School> getAllLazy();

    @Update("UPDATE school SET name=#{name}, year = #{year} WHERE id = #{id}")
    void update(School school);

    @Delete("DELETE FROM school WHERE id = #{id}")
    void delete(School school);

    @Delete("DELETE FROM school")
    void deleteAll();

    /*
     * Либо возможна трансакционная вставка School. В этом случае в пределах
     * трансакции вставляется School, затем все ее Group, все Trainee с привязкой к
     * своей Group, наконец, к каждой Group привязывается свой набор Subject. Сами
     * Subject должны быть добавлены до этой трансакционной операции.
     */
    School insertSchoolTransactional(School school2018);
}
