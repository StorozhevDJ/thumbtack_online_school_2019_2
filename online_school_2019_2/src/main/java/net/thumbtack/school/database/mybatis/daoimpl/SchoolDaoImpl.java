package net.thumbtack.school.database.mybatis.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.SchoolDao;

public class SchoolDaoImpl extends DaoImplBase implements SchoolDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDaoImpl.class);

    // вставляет School в базу данных.
    @Override
    public School insert(School school) {
        LOGGER.debug("DAO insert School {}", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert school {} {}", school, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return school;
    }

    // получает School по его ID. Если такого ID нет, метод должен возвращать null
    @Override
    public School getById(int id) {
        LOGGER.debug("DAO get School by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getSchoolMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get School by Id {} {}", id, ex);
            throw ex;
        }
    }

    // получает список всех School вместе с Group, Subject, и Trainee, используя
    // LAZY подход. Если БД не содержит ни одной School, метод должен возвращать
    // пустой список
    @Override
    public List<School> getAllLazy() {
        LOGGER.debug("DAO get all lazy ");
        try (SqlSession sqlSession = getSession()) {
            return getSchoolMapper(sqlSession).getAllLazy();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Lazy {}", ex);
            throw ex;
        }
    }

    // получает список всех School вместе с Group, Subject, и Trainee, используя
    // JOIN подход. Если БД не содержит ни одной School, метод должен возвращать
    // пустой список
    @Override
    public List<School> getAllUsingJoin() {
        LOGGER.debug("DAO get all using join ");
        try (SqlSession sqlSession = getSession()) {
            return sqlSession.selectList("net.thumbtack.school.database.mybatis.mappers.SchoolMapper.getAllUsingJoin");
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get using Join {}", ex);
            throw ex;
        }
    }

    // изменяет School в базе данных
    @Override
    public void update(School school) {
        LOGGER.debug("DAO update School {} ", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).update(school);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update School {} {} ", school, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // удаляет School в базе данных, при этом удаляются все Group
    @Override
    public void delete(School school) {
        LOGGER.debug("DAO Delete School {}", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).delete(school);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete School {} {} ", school, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // удаляет все School в базе данных, при этом удаляются все Group для каждой
    // School
    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all Schools");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Schools {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    // трансакционно вставляет School вместе с ее Group, со всеми Trainee этих
    // Group, и привязывает все Subject для этих Group
    // предполагается, что сами Subject были вставлены раньше
    @Override
    public School insertSchoolTransactional(School school) {
        LOGGER.debug("Transactional DAO Insert {} ", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school);
                for (Group group : school.getGroups()) {
                    getGroupMapper(sqlSession).insert(school, group);
                    for (Subject subject : group.getSubjects()) {
                        getGroupMapper(sqlSession).addSubjectToGroup(group, subject);
                    }
                    for (Trainee trainee : group.getTrainees()) {
                        getTraineeMapper(sqlSession).insert(group, trainee);
                    }
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert School {} {} ", school, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return school;
    }
}
