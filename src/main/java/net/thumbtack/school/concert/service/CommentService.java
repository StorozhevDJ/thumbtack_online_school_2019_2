package net.thumbtack.school.concert.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.daoimpl.CommentDaoImpl;
import net.thumbtack.school.concert.daoimpl.SessionDaoImpl;
import net.thumbtack.school.concert.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.dto.request.AddCommentDtoRequest;
import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class CommentService {

	/**
	 * Add new Comment in the DB
	 * 
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String addComment(String jsonRequest) throws ServerException {
		AddCommentDtoRequest newComment = fromJsonString(jsonRequest);
		// Find and check User
		User user = new SessionDaoImpl().get(new Session(newComment.getToken()));
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}
		// Find Song
		SongDaoImpl songDaoImpl = new SongDaoImpl();
		if (songDaoImpl.get(newComment.getSongName(), null) == null) {
			throw new ServerException(ServerErrorCode.ADD_COMMENT_ERROR);
		}
		// Find last Comment
		CommentDaoImpl commentDao = new CommentDaoImpl();
		Comment comment = commentDao.getLast(newComment.getSongName());
		// If this Comment from this author, then replace this comment
		if ((comment != null) && comment.getAuthor().equals(user.getLogin())) {
			comment.setComment(newComment.getComment());
			commentDao.update(comment);
		} else {
			// Else add new Comments for this song
			commentDao.add(new Comment(newComment.getSongName(), user.getLogin(), newComment.getComment()));
		}

		return new Gson().toJson(new ErrorDtoResponse());
	}

	/**
	 * Change existing comment
	 * 
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String changeComment(String jsonRequest) throws ServerException {
		AddCommentDtoRequest newComment = fromJsonString(jsonRequest);
		// Find and check User
		User user = new SessionDaoImpl().get(new Session(newComment.getToken()));
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}
		CommentDaoImpl commentDao = new CommentDaoImpl();
		Comment lastComment = commentDao.getLast(newComment.getSongName());
		String oldComment = commentDao.get(newComment.getSongName(), user.getLogin());
		if (oldComment == null) {
			throw new ServerException(ServerErrorCode.CHANGE_COMMENT_ERROR, " , песня или комментарий не найдены");
		}
		// If the last Comment is from this user, then replace this comment
		commentDao.delete(newComment.getSongName(), user.getLogin());// Delete old comment from DB
		if (lastComment.getAuthor().equals(user.getLogin())) {
			lastComment.setComment(newComment.getComment()); // Update text comment
			commentDao.add(lastComment); // Write to DB
		} else {// Else change author to empty from old comment and add new comment
			lastComment = new Comment(newComment.getSongName(), "", oldComment); // Delete user from comment
			commentDao.add(lastComment); // Write to DB
			commentDao.add(new Comment(newComment.getSongName(), user.getLogin(), newComment.getComment())); // Add new
																												// comment
		}
		return new Gson().toJson(new ErrorDtoResponse());
	}

	/**
	 * Disclaim from comment
	 * 
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String disclaimComment(String jsonRequest) throws ServerException {
		AddCommentDtoRequest deleteComment = fromJsonString(jsonRequest);
		// Find and check User
		User user = new SessionDaoImpl().get(new Session(deleteComment.getToken()));
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}
		// Replace the author of this comment to empty
		CommentDaoImpl commentDao = new CommentDaoImpl();
		Comment comment = new Comment(deleteComment.getSongName(), user.getLogin(), null);
		if (commentDao.update(comment)) {
			return new Gson().toJson(new ErrorDtoResponse());
		}
		throw new ServerException(ServerErrorCode.DELETE_COMMENT_ERROR);
	}

	/**
	 * Parse JSON string and validate data
	 * 
	 * @param jsonRequest
	 * @return AddCommentDtoRequest
	 * @throws ServerException
	 */
	private AddCommentDtoRequest fromJsonString(String jsonRequest) throws ServerException {
		// Parse jsonRequest to AddCommentDtoRequest
		AddCommentDtoRequest addCommentDto;
		try {
			addCommentDto = new Gson().fromJson(jsonRequest, AddCommentDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate AddCommentDtoRequest
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<AddCommentDtoRequest>> violations;
		try {
			violations = validator.validate(addCommentDto);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<AddCommentDtoRequest> cv : violations) {
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}
		return addCommentDto;
	}

}