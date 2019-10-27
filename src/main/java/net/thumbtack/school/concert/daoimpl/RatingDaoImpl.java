package net.thumbtack.school.concert.daoimpl;

import java.util.HashSet;
import java.util.List;

import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Rating;

public class RatingDaoImpl implements RatingDao {

    public boolean add(Rating rating) {
        new DataBase().insertRating(rating);
        return false;
    }

    public boolean add(List<Rating> ratings) {
        new DataBase().insertRating(new HashSet<>(ratings));
        return false;
    }

    public boolean delete(Rating rating) {
        new DataBase().deleteRating(rating.getSongName(), rating.getUser());
        return false;
    }

    public boolean update(Rating rating) {
        new DataBase().updateRating(rating);
        return false;
    }

    public List<Rating> getRatingList(String songName) {
        return new DataBase().selectRating(songName, null);
    }

    public float get(String songName) {
        List<Rating> ratingList = new DataBase().selectRating(songName, null);
        float rating = 0;
        for (Rating r : ratingList) {
            rating += r.getRating();
        }
        return rating / ratingList.size();
    }

}
