package net.thumbtack.school.database.mybatis.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.TraineeDao;

public class TraineeDaoImpl extends DaoImplBase implements TraineeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Override
    public Trainee insert(Group group, Trainee trainee) {
        LOGGER.debug("DAO insert Trainee {} with Group {}", trainee, group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).insert(group, trainee);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert trainee {} {}", trainee, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public Trainee getById(int id) {
        LOGGER.debug("DAO get Trainee by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Trainee by Id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public List<Trainee> getAll() {
        LOGGER.debug("DAO get all Trainee");
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all Trainee {}", ex);
            throw ex;
        }
    }

    @Override
    public void update(Trainee trainee) {
        LOGGER.debug("DAO update Trainee {} ", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).update(trainee);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update Trainee {} {} ", trainee, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating) {
        LOGGER.debug("DAO get all Trainee with params {} {} {}", firstName, lastName, rating);
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getAllWithParams(firstName, lastName, rating);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert get All Trainee with params {}", ex);
            throw ex;
        }
    }

    @Override
    public void batchInsert(List<Trainee> trainees) {
        LOGGER.debug("DAO batch Trainee insert {}", trainees);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).batchInsert(trainees);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't batch insert Trainee {} {} ", trainees, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(Trainee trainee) {
        LOGGER.debug("DAO delete Trainee {} ", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).delete(trainee);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Trainee {} {}", trainee, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all Trainee");
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Trainee {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

}
