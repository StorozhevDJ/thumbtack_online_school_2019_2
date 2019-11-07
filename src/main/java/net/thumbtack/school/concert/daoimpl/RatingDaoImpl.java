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

    public boolean delete(String songName, String user) {
        return new DataBase().deleteRating(songName, user);
    }

    public boolean update(Rating rating) {
        return new DataBase().updateRating(rating);
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
