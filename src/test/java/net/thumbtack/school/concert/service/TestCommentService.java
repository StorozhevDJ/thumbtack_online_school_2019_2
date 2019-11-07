package net.thumbtack.school.concert.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

public class TestCommentService {
	@Test
	public void testAddComment() {

		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		CommentService cs = new CommentService();

		try {
			cs.addComment(null);
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			cs.addComment("");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			cs.addComment("asdaqr");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			cs.addComment(
					"{\"songName\":\"songName1011\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.ADD_COMMENT_ERROR, e.getServerErrorCode());
		}
		try {
			cs.addComment(
					"{\"songName\":\"\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
		}
		try {
			cs.addComment(
					"{\"songName\":\"songName10\",\"comment\":\"TestComment\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
		}
		int comSize = db.getComments().size();
		try {
			cs.addComment(
					"{\"songName\":\"songName10\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize + 1, db.getComments().size());
		comSize = db.getComments().size();
		try {
			cs.addComment(
					"{\"songName\":\"songName10\",\"comment\":\"TestComment2\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize, db.getComments().size());
		comSize = db.getComments().size();
		try {
			cs.addComment(
					"{\"songName\":\"songName10\",\"comment\":\"TestComment2\",\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize + 1, db.getComments().size());

		db.close();
	}

	@Test
	public void testChangeComment() {

		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		CommentService cs = new CommentService();

		try {
			cs.changeComment(
					"{\"songName\":\"songName10\",\"comment\":\"TestComment\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
		}
		try {
			cs.changeComment(
					"{\"songName\":\"songName10\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CHANGE_COMMENT_ERROR, e.getServerErrorCode());
		}
		try {
			cs.changeComment(
					"{\"songName\":\"songName11\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CHANGE_COMMENT_ERROR, e.getServerErrorCode());
		}
		int comSize = db.getComments().size();
		try {
			cs.changeComment(
					"{\"songName\":\"songName11\",\"comment\":\"NewTestComment\",\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize + 1, db.getComments().size());
		comSize = db.getComments().size();
		try {
			cs.changeComment(
					"{\"songName\":\"songName11\",\"comment\":\"NewTestComment2\",\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize, db.getComments().size());

		db.close();
	}

	@Test
	public void testDisclaimComment() {

		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		CommentService cs = new CommentService();

		try {
			cs.disclaimComment(
					"{\"songName\":\"songName10\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
		}
		try {
			cs.disclaimComment(
					"{\"songName\":\"songName10\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.DELETE_COMMENT_ERROR, e.getServerErrorCode());
		}
		try {
			cs.disclaimComment(
					"{\"songName\":\"songName11\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.DELETE_COMMENT_ERROR, e.getServerErrorCode());
		}
		int comSize = db.getComments().size();
		try {
			cs.disclaimComment(
					"{\"songName\":\"songName11\",\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize, db.getComments().size());
		comSize = db.getComments().size();
		try {
			cs.disclaimComment(
					"{\"songName\":\"songName11\",\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		assertEquals(comSize, db.getComments().size());

		db.close();
	}
}
