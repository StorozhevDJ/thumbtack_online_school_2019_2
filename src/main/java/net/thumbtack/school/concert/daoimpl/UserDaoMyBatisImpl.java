package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.daoimpl.DaoMyBatisImplBase;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserDaoMyBatisImpl extends DaoMyBatisImplBase implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoMyBatisImpl.class);

    @Override
    public void add(User user) throws ServerException {
        LOGGER.debug("DAO insert User {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert User {}. {}", user, ex.getCause().getMessage());
                sqlSession.rollback();
                //throw ex;
                throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
            }
            sqlSession.commit();
        }
    }

    @Override
    public User get(String login) {
        LOGGER.debug("DAO get Trainee by Id {}", login);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getByLogin(login);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get User by Login {}. {}.", login, ex);
            //throw ex;
        }
        return null;
    }

    @Override
    public void delete(String login) {
        LOGGER.debug("DAO delete User with ligin {}", login);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).delete(login);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {}.\r\n {}.", login, ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }

    public void deleteAll() {
        LOGGER.debug("DAO delete all Users");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Users {}.", ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }

    public void addAll(Map<String, User> users) throws ServerException {
        LOGGER.debug("DAO insert Users {}", users);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insertAll(users);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert User {}. {}", users, ex.getCause().getMessage());
                sqlSession.rollback();
                throw ex;
                //throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, users.getLogin());
            }
            sqlSession.commit();
        }
    }
}
