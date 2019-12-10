package net.thumbtack.school.concert.daoimpl;

import java.util.ArrayList;
import java.util.List;
import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Rating;

public class RatingDaoImpl implements RatingDao {

    public boolean add(Rating rating) {
        return new DataBase().insertRating(rating);
    }

    public boolean add(List<Rating> ratings) {
        return new DataBase().insertRating(new ArrayList<>(ratings));
    }

    public boolean update(Rating rating) {
        if (((rating.getUser() != null) && (!rating.getUser().isEmpty()))
                && ((rating.getSongId() != null) && (!rating.getSongId().isEmpty()))) {
            return new DataBase().updateRating(rating);
        }
        return false;
    }

    public List<Rating> getRatingList(String songId) {
        return new DataBase().selectRating(songId, null);
    }

    public List<Rating> getRatingList(String songId, String user) {
        return new DataBase().selectRating(songId, user);
    }

    public List<Rating> getRatingList(List<String> songId) {
        return new DataBase().selectRating(songId);
    }

    public float get(String songId) {
        List<Rating> ratingList = new DataBase().selectRating(songId, null);
        float rating = 0;
        for (Rating r : ratingList) {
            rating += r.getRating();
        }
        return rating / ratingList.size();
    }

    public List<Rating> get(List<String> songsIdList) {
        return new DataBase().selectRating(songsIdList);
    }

    public boolean delete(String songId, String user) {
        return new DataBase().deleteRating(songId, user);
    }

}
