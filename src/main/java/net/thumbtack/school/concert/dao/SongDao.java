package net.thumbtack.school.concert.dao;

import java.util.List;

import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public interface SongDao {

    /**
     * Add the Song in the DataBase
     *
     * @param songModel Song Model to add to the DataBase
     * @param user      - User song uploader
     * @throws ServerException
     */
    void add(List<Song> songModel, User user) throws ServerException;

    /**
     * Get all song with filter
     *
     * @param composer filter for one or more composer
     * @param author   filter for one or more author
     * @param singer   filter for singer
     * @return Song array
     */
    List<Song> get(List<String> composer, List<String> author, String singer);

    List<Song> get(String user);
}
