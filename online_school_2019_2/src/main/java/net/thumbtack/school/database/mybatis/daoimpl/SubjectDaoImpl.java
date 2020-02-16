package net.thumbtack.school.database.mybatis.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.mybatis.dao.SubjectDao;

public class SubjectDaoImpl extends DaoImplBase implements SubjectDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @Override
    public Subject insert(Subject subject) {
        LOGGER.debug("DAO insert Subject {}", subject);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).insert(subject);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert subject {} {}", subject, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return subject;
    }

    @Override
    public Subject getById(int id) {
        LOGGER.debug("DAO get Subject by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Subject by Id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public List<Subject> getAll() {
        LOGGER.debug("DAO get all Subject");
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all Subject {}", ex);
            throw ex;
        }
    }

    @Override
    public Subject update(Subject subject) {
        LOGGER.debug("DAO update Subject {} ", subject);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).update(subject);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update Subject {} {} ", subject, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
            return subject;
        }
    }

    @Override
    public void delete(Subject subject) {
        LOGGER.debug("DAO Delete Subject {}", subject);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).delete(subject);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Subject {} {} ", subject, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all Subject");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Subject {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

}
