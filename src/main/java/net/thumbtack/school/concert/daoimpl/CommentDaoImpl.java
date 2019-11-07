package net.thumbtack.school.concert.daoimpl;

import java.util.List;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Comment;

public class CommentDaoImpl implements CommentDao {

	public boolean add(Comment comment) {
		return new DataBase().insertComment(comment);
	}

	public List<Comment> get(String songName) {
		return new DataBase().selectComment(songName, null);
	}

	public String get(String songName, String author) {
		List<Comment> com = new DataBase().selectComment(songName, author);
		return com.isEmpty()?null:com.get(0).getComment();
	}

	public Comment getLast(String songName) {
		List<Comment> comment = new DataBase().selectComment(songName, null);
		if (comment.size() >= 1) {
			return comment.get(comment.size() - 1);
		} else {
			return null;
		}
	}

	public boolean update(Comment comment) {
		return new DataBase().updateComment(comment);
	}

	public boolean delete(Comment comment) {
		return new DataBase().deleteComment(comment);
	}

	public boolean delete(String songName, String author) {
		return new DataBase().deleteComment(songName, author);
	}
}