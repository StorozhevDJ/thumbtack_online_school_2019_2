package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionDaoMyBatisImpl extends DaoMyBatisImplBase implements SessionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDaoMyBatisImpl.class);

    @Override
    public void login(User user, Session session) {
        LOGGER.debug("DAO login User {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).insert(user, session.getToken());
            } catch (RuntimeException ex) {
                LOGGER.info("Can't logined User {}. {}", user, ex.getCause().getMessage());
                sqlSession.rollback();
                //throw ex;
                //throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
            }
            sqlSession.commit();
        }
        new DataBase().insertSession(user, session);
    }

    @Override
    public void logout(Session session) {
        LOGGER.debug("DAO delete user session by Token {}", session.getToken());
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).deleteByToken(session.getToken());
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete user session by Token {}. {}", session.getToken(), ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public boolean checkSession(Session session) {
        LOGGER.debug("DAO check session by Token {}", session.getToken());
        try (SqlSession sqlSession = getSession()) {
            return getSessionMapper(sqlSession).getByToken(session.getToken()) == null;
        } catch (RuntimeException ex) {
            LOGGER.info("Can't check session by Token {}. {}.", session.getToken(), ex);
            //throw ex;
        }
        return false;
    }

    @Override
    public User get(Session session) {
        LOGGER.debug("DAO check session by Token {}", session.getToken());
        try (SqlSession sqlSession = getSession()) {
            return getSessionMapper(sqlSession).getUser(session.getToken());
        } catch (RuntimeException ex) {
            LOGGER.info("Can't check session by Token {}. {}.", session.getToken(), ex);
            //throw ex;
        }
        return null;
    }

    public void deleteAll() {
        LOGGER.debug("DAO delete all users sessions");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all users sessiuns {}", ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }
}
