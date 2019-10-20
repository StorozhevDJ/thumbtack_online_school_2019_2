package net.thumbtack.school.concert.daoimpl;

import java.util.HashSet;
import java.util.List;

import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Rating;

public class RatingDaoImpl extends DataBase implements RatingDao {

	public boolean insert(Rating rating) {
		insertRating(rating);
		return false;
	}

	public boolean insert(List<Rating> ratings) {
		insertRating(new HashSet<>(ratings));
		return false;
	}

	public boolean deleteRating(Rating rating) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateRating(Rating rating) {
		// TODO Auto-generated method stub
		return false;
	}

}
