package net.thumbtack.school.concert.dao;

import java.util.List;

import net.thumbtack.school.concert.model.Rating;

public interface RatingDao {

	/**
	 * Add riting in DB
	 *
	 * @param rating
	 * @return
	 */
	boolean add(Rating rating);

	/**
	 * Add ratings in DB
	 *
	 * @param ratings
	 * @return
	 */
	boolean add(List<Rating> ratings);

	/**
	 * Delete Rating from DB
	 *
	 * @param rating
	 * @return
	 */
	boolean delete(Rating rating);

	/**
	 * Update existing rating
	 * @param rating
	 * @return
	 */
	boolean update(Rating rating);

	/**
	 * Get rating List for songName
	 *
	 * @param songName
	 * @return
	 */
	List<Rating> getRatingList(String songName);

	/**
	 * Get an average rating for all rating of the Song with name songname
	 * 
	 * @param songName
	 * @return float - average rating
	 */
	float get(String songName);

}
