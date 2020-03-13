package net.thumbtack.school.concert.mappers;

import net.thumbtack.school.concert.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {

    @Insert("INSERT INTO comments (songId, comment, author) VALUES "
            + "( #{songId}, #{comment}, #{author} )")
    @Options(useGeneratedKeys = true)
    void insert(Comment comment);

    @Select("SELECT * FROM comments WHERE songId = #{songId}")
    List<Comment> getBySongId(String songId);

    List<Comment> getBySongId(List<String> songId);

    @Select("SELECT * FROM comments WHERE songId = #{songId}")
    String get(String songId, String author);

    List<Comment> getList(String songId, String author);

    Comment getLast(String songId);

    @Update("UPDATE comments SET comment = #{comment.comment} WHERE id = #{id}")
    void update(Comment comment);

    void update(List<Comment> comments);

    void delete(Comment comment);

    void delete(String songId, String author);

    @Delete("DELETE FROM comments")
    void deleteAll();
}
