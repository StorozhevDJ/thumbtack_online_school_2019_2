package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.model.Comment;

import java.util.List;

public interface CommentDao {

	/**
	 * Add comment in DB
	 *
	 * @param comment
	 * @return
	 */
	boolean add(Comment comment);

	List<String> get(String songName);

	boolean delete (Comment comment);

	boolean delete (String songName, String author);
}
