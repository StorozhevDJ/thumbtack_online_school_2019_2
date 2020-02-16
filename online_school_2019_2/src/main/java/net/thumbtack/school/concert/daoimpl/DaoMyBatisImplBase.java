package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.mappers.*;
import net.thumbtack.school.concert.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoMyBatisImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected CommentMapper getCommentMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(CommentMapper.class);
    }

    protected RatingMapper getRatingMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(RatingMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected SongMapper getSongMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SongMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }
}