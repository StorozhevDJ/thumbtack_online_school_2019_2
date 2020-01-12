package net.thumbtack.school.concert.daoimpl;

import java.util.List;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Comment;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentDaoMyBatisImpl extends DaoMyBatisImplBase implements CommentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentDaoMyBatisImpl.class);

    @Override
    public boolean add(Comment comment) {
        return new DataBase().insertComment(comment);
    }

    @Override
    public List<Comment> get(String songId) {
        return new DataBase().selectComment(songId, null);
    }

    @Override
    public List<Comment> get(List<String> songId) {
        return new DataBase().selectComment(songId);
    }

    @Override
    public String get(String songId, String author) {
        List<Comment> com = new DataBase().selectComment(songId, author);
        return com.isEmpty() ? null : com.get(0).getComment();
    }

    @Override
    public List<Comment> getList(String songId, String author) {
        return new DataBase().selectComment(songId, author);
    }

    @Override
    public Comment getLast(String songId) {
        List<Comment> comment = new DataBase().selectComment(songId, null);
        return (comment.size() >= 1) ? comment.get(comment.size() - 1) : null;
    }

    @Override
    public boolean update(Comment comment) {
        return new DataBase().updateComment(comment);
    }

    @Override
    public boolean update(List<Comment> comments) {
        return new DataBase().updateComment(comments);
    }

    @Override
    public boolean delete(Comment comment) {
        return new DataBase().deleteComment(comment);
    }

    @Override
    public boolean delete(String songId, String author) {
        return new DataBase().deleteComment(songId, author);
    }

    public void deleteAll() {
        LOGGER.debug("DAO delete all Comments");
        try (SqlSession sqlSession = getSession()) {
            try {
                getCommentMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Comments {}.", ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }
}
