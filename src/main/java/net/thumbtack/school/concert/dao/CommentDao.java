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
     * Get all comments for this song ID
     *
     * @param songId
     * @return
     */
    List<Comment> get(String songId);

    /**
     * Get comment for this sing ID with the author
     *
     * @param songId
     * @param author
     * @return
     */
    String get(String songId, String author);

    /**
     * Get all comment for this song ID and/or author
     *
     * @param songId
     * @param author
     * @return
     */
    List<Comment> getList(String songId, String author);

    /**
     * Get last Comment for this song
     *
     * @param songId
     * @return
     */
    Comment getLast(String songId);

    /**
     * Update comment
     *
     * @param comment
     * @return
     */
    boolean update(Comment comment);

    /**
     * Update comment
     *
     * @param comments list
     * @return
     */
    boolean update(List<Comment> comments);

    /**
     * Delete comment for Song
     *
     * @param comment
     * @return
     */
    boolean delete(Comment comment);

    /**
     * Delete comment for this songName and author
     *
     * @param songId
     * @param author
     * @return
     */
    boolean delete(String songId, String author);

    /**
     * Get comment list from Song Id list
     *
     * @param songId
     * @return
     */
    List<Comment> get(List<String> songId);
}