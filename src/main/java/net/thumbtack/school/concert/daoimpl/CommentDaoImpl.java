package net.thumbtack.school.concert.daoimpl;

import java.util.List;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Comment;

public class CommentDaoImpl implements CommentDao {

    public boolean add(Comment comment) {
        return new DataBase().insertComment(comment);
    }
    
    public List<Comment> get(String songName){
    	return new DataBase().selectComment(songName, null);
    }
    
    public String get(String songName, String author){
    	return new DataBase().selectComment(songName, author).get(0).getComment();
    }

    public boolean delete (Comment comment){
        return new DataBase().deleteComment(comment);
    }

    public boolean delete (String songName, String author){
        return new DataBase().deleteComment(songName, author);
    }
}
