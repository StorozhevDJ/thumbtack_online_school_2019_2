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

	/**
	 * Get all comments for this songName
	 * 
	 * @param songName
	 * @return
	 */
	List<Comment> get(String songName);
	
	/**
	 * Get comment for this singName with the author
	 * 
	 * @param songName
	 * @param author
	 * @return
	 */
	String get(String songName, String author);
	
	/**
	 * Get last Comment for this song
	 * 
	 * @param songName
	 * @return
	 */
	Comment getLast(String songName);
	
	/**
	 * Update comment
	 * 
	 * @param comment
	 * @return
	 */
	boolean update (Comment comment);

	/**
	 * Delete comment for Song
	 * 
	 * @param comment
	 * @return
	 */
	boolean delete (Comment comment);

	/**
	 * Delete comment for this songName and author
	 * 
	 * @param songName
	 * @param author
	 * @return
	 */
	boolean delete (String songName, String author);
}
