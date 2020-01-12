package net.thumbtack.school.concert.daoimpl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.concert.dao.CommonDao;
import net.thumbtack.school.concert.daoimpl.DaoMyBatisImplBase;
import net.thumbtack.school.concert.model.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class CommonDaoMyBatisImpl extends DaoMyBatisImplBase implements CommonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoMyBatisImplBase.class);

    /**
     * Read and open JSON file with DataBase data (restore backup to database)
     *
     * @param fileName filename with data in JSON format
     * @throws JsonSyntaxException
     * @throws IOException
     */
    public void open(String fileName) throws JsonSyntaxException, IOException {
        File dbFile = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
            Map<String, User> users = new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, User>>() {
            }.getType());
            BidiMap<String, Session> sessions = new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, Session>>() {
            }.getType());
            Map<String, Song> songs = new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, Song>>() {
            }.getType());
            Set<Comment> comments = new Gson().fromJson(reader.readLine(), new TypeToken<Set<Comment>>() {
            }.getType());
            Set<Rating> ratings = new Gson().fromJson(reader.readLine(), new TypeToken<Set<Rating>>() {
            }.getType());
        }
    }

    /**
     * Initialize DataBase with default data
     */
    public void open() {

    }

    /**
     * Save DataBease data in file and close
     *
     * @param fileName to save data
     * @throws FileNotFoundException
     */
    public void close(String fileName) throws FileNotFoundException {
        /*try (PrintWriter pw = new PrintWriter(new File(fileName))) {
            pw.println(new Gson().toJson(users));
            pw.println(new Gson().toJson(sessions));
            pw.println(new Gson().toJson(songs));
            pw.println(new Gson().toJson(comments));
            pw.println(new Gson().toJson(ratings));
        }
        close();*/
    }

    /**
     * Close DataBase - Clear all data
     */
    public void close() {

    }

    @Override
    public void clear() {
        LOGGER.debug("Clear Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getCommentMapper(sqlSession).deleteAll();
                getRatingMapper(sqlSession).deleteAll();
                getSessionMapper(sqlSession).deleteAll();
                getUserMapper(sqlSession).deleteAll();
                getSongMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
