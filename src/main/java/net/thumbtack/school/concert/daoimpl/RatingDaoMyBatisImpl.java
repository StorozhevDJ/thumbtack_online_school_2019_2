package net.thumbtack.school.concert.daoimpl;

import java.util.ArrayList;
import java.util.List;

import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Rating;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RatingDaoMyBatisImpl extends DaoMyBatisImplBase implements RatingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingDaoMyBatisImpl.class);

    @Override
    public boolean add(Rating rating) {
        return new DataBase().insertRating(rating);
    }

    @Override
    public boolean add(List<Rating> ratings) {
        return new DataBase().insertRating(new ArrayList<>(ratings));
    }

    @Override
    public boolean update(Rating rating) {
        if (((rating.getUser() != null) && (!rating.getUser().isEmpty()))
                && ((rating.getSongId() != null) && (!rating.getSongId().isEmpty()))) {
            return new DataBase().updateRating(rating);
        }
        return false;
    }

    @Override
    public List<Rating> getRatingList(String songId) {
        return new DataBase().selectRating(songId, null);
    }

    @Override
    public List<Rating> getRatingList(String songId, String user) {
        return new DataBase().selectRating(songId, user);
    }

    @Override
    public List<Rating> getRatingList(List<String> songId) {
        return new DataBase().selectRating(songId);
    }

    @Override
    public float get(String songId) {
        List<Rating> ratingList = new DataBase().selectRating(songId, null);
        float rating = 0;
        for (Rating r : ratingList) {
            rating += r.getRating();
        }
        return rating / ratingList.size();
    }

    @Override
    public List<Rating> get(List<String> songsIdList) {
        return new DataBase().selectRating(songsIdList);
    }

    @Override
    public boolean delete(String songId, String user) {
        return new DataBase().deleteRating(songId, user);
    }

    public void deleteAll() {
        LOGGER.debug("DAO delete all Ratings");
        try (SqlSession sqlSession = getSession()) {
            try {
                getRatingMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Ratings {}.", ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }
}
