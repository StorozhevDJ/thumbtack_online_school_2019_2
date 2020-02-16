package net.thumbtack.school.concert.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public interface SessionMapper {

    @Insert("INSERT INTO sessions (login, token) VALUES (#{user.login}, #{token} )")
    @Options(useGeneratedKeys = true, keyProperty = "user.login")
    Integer insert(@Param("user") User user, @Param("token") String token);

    @Select("SELECT * FROM sessions WHERE token=#{token};")
    Session getByToken(String token);

    @Select("SELECT * FROM users JOIN sessions USING (login);")
    User getUser(String token);

    @Delete("DELETE FROM sessions WHERE token=#{token};")
    void deleteByToken(String token);

    @Delete("DELETE FROM sessions")
    void deleteAll();
}
