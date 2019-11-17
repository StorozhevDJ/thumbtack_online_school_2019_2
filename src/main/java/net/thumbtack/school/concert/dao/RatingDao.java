package net.thumbtack.school.concert.dao;

import java.util.List;

import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Song;

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
     * Get rating List for songName
     *
     * @param songName
     * @return
     */
    List<Rating> getRatingList(String songName);

    /**
     * Get rating List for songName and user
     *
     * @param songName
     * @return
     */
    List<Rating> getRatingList(String songName, String user);

    /**
     * Get rating list for list of song name
     *
     * @param songName
     * @return
     */
    List<Rating> getRatingList(List<String> songName);

    /**
     * Get an average rating for all rating of the Song with name songname
     *
     * @param songName
     * @return float - average rating
     */
    float get(String songName);

    List<Rating> get(List<Song> songs);

    /**
     * Delete Rating with Song Name and User Login from DB
     *
     * @param songName
     * @param user
     * @return <tt>true</tt> if this rating did not already contain in db
     */
    boolean delete(String songName, String user);
}
