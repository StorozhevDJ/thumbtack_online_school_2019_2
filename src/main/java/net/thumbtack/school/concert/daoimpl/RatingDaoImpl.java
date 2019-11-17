package net.thumbtack.school.concert.daoimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class RatingDaoImpl implements RatingDao {

    public boolean add(Rating rating) {
        return new DataBase().insertRating(rating);
    }

    public boolean add(List<Rating> ratings) {
        return new DataBase().insertRating(new ArrayList<>(ratings));
    }

    public boolean update(Rating rating) {
        if (((rating.getUser() != null) && (!rating.getUser().isEmpty()))
                && ((rating.getSongName() != null) && (!rating.getSongName().isEmpty()))) {
            return new DataBase().updateRating(rating);
        }
        return false;
    }

    public List<Rating> getRatingList(String songName) {
        return new DataBase().selectRating(songName, null);
    }

    public List<Rating> getRatingList(String songName, String user) {
        return new DataBase().selectRating(songName, user);
    }

    public List<Rating> getRatingList(List<String> songName) {
        return new DataBase().selectRating(songName);
    }

    public float get(String songName) {
        List<Rating> ratingList = new DataBase().selectRating(songName, null);
        float rating = 0;
        for (Rating r : ratingList) {
            rating += r.getRating();
        }
        return rating / ratingList.size();
    }

    public List<Rating> get(List<Song> songs) {
        List<String> songNameList = new ArrayList<>();
        for (Song s : songs) {
            songNameList.add(s.getSongName());
        }
        return new DataBase().selectRating(songNameList);
    }

    public boolean delete(String songName, String user) {
        return new DataBase().deleteRating(songName, user);
    }

}
