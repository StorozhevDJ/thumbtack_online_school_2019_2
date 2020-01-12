package net.thumbtack.school.concert.mappers;

import net.thumbtack.school.concert.model.Comment;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface CommentMapper {

    void add(Comment comment);

    List<Comment> get(String songId);

    List<Comment> get(List<String> songId);

    String get(String songId, String author);

    List<Comment> getList(String songId, String author);

    Comment getLast(String songId);

    void update(Comment comment);

    void update(List<Comment> comments);

    void delete(Comment comment);

    void delete(String songId, String author);

    @Delete("DELETE FROM comments")
    void deleteAll();
}
