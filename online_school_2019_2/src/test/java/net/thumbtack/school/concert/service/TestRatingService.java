package net.thumbtack.school.concert.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

public class TestRatingService {
	
	@Test
	public void testAddRating() {

		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		RatingService rs = new RatingService();
		
		try {
			rs.addRating(null);
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			rs.addRating("");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			rs.addRating("asdaqr");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			rs.addRating("{\"songId\":\"songT1111\",\"rating\":\"4\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.ADD_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.addRating("{\"songId\":\"songT1\",\"rating\":\"6\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
		}
		try {
			rs.addRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
		}
		try {
			rs.addRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		try {
			rs.addRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.ADD_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.addRating("{\"songId\":\"songT1\",\"rating\":\"3\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.ADD_RATING_ERROR, e.getServerErrorCode());
		}
		
		db.close();
	}

	@Test
	public void testUpdateRating() {

		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		RatingService rs = new RatingService();
		
		try {
			rs.changeRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
		}
		try {
			rs.changeRating("{\"songId\":\"songT1\",\"rating\":\"3\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CHANGE_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.changeRating("{\"songId\":\"songT1\",\"rating\":\"3\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CHANGE_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.changeRating("{\"songId\":\"songT1\",\"rating\":\"3\",\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CHANGE_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.changeRating("{\"songId\":\"songT3\",\"rating\":\"3\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		
		db.close();
	}
	
	@Test
	public void testDeleteRating() {

		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		RatingService rs = new RatingService();
		
		try {
			rs.deleteRating(null);
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			rs.deleteRating("");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			rs.deleteRating("asdaqr");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
		}
		try {
			rs.deleteRating("{\"songId\":\"songT1\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
		}
		try {
			rs.deleteRating("{\"songId\":\"songT4\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.DELETE_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.deleteRating("{\"songId\":\"songT1\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
			fail();
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.DELETE_RATING_ERROR, e.getServerErrorCode());
		}
		try {
			rs.deleteRating("{\"songId\":\"songT3\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		
		db.close();
	}
}
