package net.thumbtack.school.concert.mappers;

import net.thumbtack.school.concert.model.Song;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SongMapper {


    void add(Map<Integer, Song> songModel);

    Map<Integer, Song> get(List<String> composer, List<String> author, String singer);

    @Select("<select id='id' parameterType='int' resultType='hashmap'>" +
            "SELECT * FROM songs WHERE userLogin = #{user}" +
            "</select>")
        //@Select("SELECT * FROM songs WHERE userLogin = #{user}")
    Map<Integer, Song> get(String user);

    @Select("SELECT * FROM songs WHERE id = #{songId}")
    Song getById(Integer songId);

    void update(Song song, Integer songId);

    @Delete({"<script>",
            "DELETE FROM songs WHERE ",
            "<foreach item='item' collection='list' separator=' OR id='>",
            "#{songId}",
            "</foreach>",
            "</script>"})
    void delete(@Param("list") List<Integer> songsId);

    @Delete("DELETE FROM songs WHERE id = #{songId}")
    void deleteById(Integer songId);

    @Delete("DELETE FROM songs")
    void deleteAll();
}
