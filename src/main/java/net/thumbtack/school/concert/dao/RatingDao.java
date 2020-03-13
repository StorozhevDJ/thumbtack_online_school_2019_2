package net.thumbtack.school.concert.dao;

import java.util.List;

import net.thumbtack.school.concert.model.Rating;

public interface RatingDao {

    /**
     * Add rating in DB
     *
     * @param rating
     * @return <tt>true</tt> if this rating did not already contain in db
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
     * Update existing rating with specified song name and  user
     *
     * @param rating
     * @return <tt>true</tt> if this rating updates
     */
    boolean update(Rating rating);

    /**
     * Get rating List for song ID
     *
     * @param songId
     * @return
     */
    List<Rating> getRatingList(String songId);

    /**
     * Get rating List for song ID and user
     *
     * @param songId
     * @return
     */
    List<Rating> getRatingList(String songId, String user);

    /**
     * Get rating list for list of song ID
     *
     * @param songId
     * @return
     */
    List<Rating> getRatingList(List<String> songId);

    /**
     * Get an average rating for all rating of the Song with song ID
     *
     * @param songId
     * @return float - average rating
     */
    float get(String songId);

    /**
     * <i>Get an average rating</i> for all rating of the Song with song ID
     *
     * @param songsIdList
     * @return float - average rating
     */
    List<Rating> get(List<String> songsIdList);

    /**
     * <tt>Delete</tt> Rating with Song Name and User Login from DB
     *
     * @param songId
     * @param user
     * @return <tt>true</tt> if this rating did not already contain in db
     */
    boolean delete(String songId, String user);
}
