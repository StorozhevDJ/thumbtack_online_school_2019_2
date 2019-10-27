package net.thumbtack.school.concert.daoimpl;

import java.util.List;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Comment;

public class CommentDaoImpl implements CommentDao {

    public boolean add(Comment comment) {
        return new DataBase().insertComment(comment);
    }
    
    public List<String> get(String songName){
    	return new DataBase().selectComment(songName, null);
    }

    public boolean delete (Comment comment){
        // TODO add code
        return false;
    }

    public boolean delete (String songName, String author){
        // TODO add code
        return false;
    }
}
