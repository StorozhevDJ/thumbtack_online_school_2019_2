package net.thumbtack.school.concert.dao;

import java.util.List;
import java.util.Map;

import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Song;

public interface SongDao {

    /**
     * Add the Song in the DataBase
     *
     * @param songModel Song Model to add to the DataBase
     * @throws ServerException
     */
    void add(Map<String, Song> songModel);

    /**
     * Get all song with filter
     *
     * @param composer filter for one or more composer
     * @param author   filter for one or more author
     * @param singer   filter for singer
     * @return Song array
     */
    Map<String, Song> get(List<String> composer, List<String> author, String singer);

    /**
     * Get songs list from this user
     *
     * @param user
     * @return
     */
    Map<String, Song> get(String user);

    /**
     * Get song from this user and/or song name
     *
     * @param songId
     * @param user
     * @return
     */
    Song get(String songId, String user);

    /**
     * Delete this song
     *
     * @param songId
     * @return
     */
    boolean delete(String songId);

    /**
     * Delete Song List
     *
     * @param songsId list
     * @return
     */
    boolean delete(List<String> songsId);

    /**
     * Update song
     *
     * @param song
     * @param songId
     * @return
     */
    boolean update(Song song, String songId);

    /**
     * Update song list
     *
     * @param songs - map
     * @return
     */
    void update(Map<String, Song> songs);
}
