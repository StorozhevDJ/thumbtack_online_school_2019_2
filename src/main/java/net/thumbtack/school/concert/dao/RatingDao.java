package net.thumbtack.school.concert.dao;

import java.util.List;

import net.thumbtack.school.concert.model.Rating;

public interface RatingDao {

    boolean insert(Rating rating);

    boolean insert(List<Rating> ratings);

    boolean deleteRating(Rating rating);

    boolean updateRating(Rating rating);
	
}
