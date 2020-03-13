package net.thumbtack.school.concert.mappers;

import net.thumbtack.school.concert.model.User;

import org.apache.ibatis.annotations.*;

import java.util.Map;

public interface UserMapper {

    @Insert("INSERT INTO users (firstName, lastName, login, `password`) VALUES "
            + "( #{firstName}, #{lastName}, #{login}, #{password} )")
    @Options(useGeneratedKeys = true)
    Integer insert(User user);

    /*@Insert("INSERT INTO users (firstName, lastName, login, `password`) VALUES "
            + "( #{firstName}, #{lastName}, #{login}, #{password} )")*/
    @Insert("INSERT INTO users (id, login, firstName, lastName, password)"
            + "VALUES"
            + "<foreach item='item' seperator=',' collection=#{entries}>"
            + "( #{item.key)}, #{item.value.login}, #{item.value.firstName},#{item.value.lastName},#{item.value.password})"
            + "</foreach>")
    Integer insertAll(@Param("entries") Map<String, User> users);

    @Select("SELECT * FROM users WHERE login = #{login}")
    User getByLogin(String login);

    @Delete("DELETE FROM users WHERE login = #{login}")
    void delete(String login);

    @Delete("DELETE FROM users")
    void deleteAll();
}
