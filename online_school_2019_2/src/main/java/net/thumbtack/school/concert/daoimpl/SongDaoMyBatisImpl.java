package net.thumbtack.school.concert.daoimpl;

import java.util.List;
import java.util.Map;

import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Song;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for save Song date
 *
 * @author Denis
 */
public class SongDaoMyBatisImpl extends DaoMyBatisImplBase implements SongDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SongDaoMyBatisImpl.class);

    @Override
    public void add(Map<String, Song> songModel) {
        DataBase db = new DataBase();
        db.insertSongs(songModel); // Add new songs into the DataBase
    }

    @Override
    public Map<String, Song> get(List<String> composer, List<String> author, String singer) {
        return new DataBase().selectSong(new Song(null, composer, author, singer, 0, null));
    }

    @Override
    public Map<String, Song> get(String user) {
        return new DataBase().selectSong(new Song(null, null, null, null, 0, user));
    }

    @Override
    public Song get(String songId, String user) {
        Song song = new DataBase().selectSong(songId);
        if (song == null) {
            return null;
        }
        if (user != null) {
            return song.getUserLogin().equals(user) ? song : null;
        } else {
            return song;
        }
    }

    @Override
    public boolean delete(String songId) {
        LOGGER.debug("DAO delete Song by ID {}", songId);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSongMapper(sqlSession).deleteById(Integer.parseInt(songId));
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete Song by ID {}. {}", songId, ex);
                sqlSession.rollback();
                //throw ex;
                return false;
            }
            sqlSession.commit();
        }
        return true;
    }

    @Override
    public boolean delete(List<String> songsId) {
        return new DataBase().deleteSong(songsId);
    }

    @Override
    public boolean update(Song song, String songId) {
        return new DataBase().updateSong(song, songId);
    }

    @Override
    public void update(Map<String, Song> songs) {
        new DataBase().updateSong(songs);
    }

    public void deleteAll() {
        LOGGER.debug("DAO delete all Songs");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSongMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all Songs {}.", ex);
                sqlSession.rollback();
                //throw ex;
            }
            sqlSession.commit();
        }
    }
}
