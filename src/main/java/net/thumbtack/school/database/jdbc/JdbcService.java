package net.thumbtack.school.database.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;

public class JdbcService {

	/**
	 * Добавляет Trainee в базу данных.
	 * 
	 * @param trainee
	 * @throws SQLException
	 */
	public static void insertTrainee(Trainee trainee) throws SQLException {
		String insertQuery = "INSERT INTO trainee values(?,?,?,?,?)";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setNull(1, java.sql.Types.INTEGER);
			stmt.setNull(2, java.sql.Types.INTEGER);
			stmt.setString(3, trainee.getFirstName());
			stmt.setString(4, trainee.getLastName());
			stmt.setInt(5, trainee.getRating());
			stmt.executeUpdate();
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					trainee.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	/**
	 * Изменяет ранее записанный Trainee в базе данных. В случае ошибки выбрасывает
	 * SQLException.
	 * 
	 * @param trainee
	 * @throws SQLException
	 */
	public static void updateTrainee(Trainee trainee) throws SQLException {
		String updateQuery = "UPDATE trainee SET firstName = ?, lastName=?, rating=? where id=?";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
			stmt.setString(1, trainee.getFirstName());
			stmt.setString(2, trainee.getLastName());
			stmt.setInt(3, trainee.getRating());
			stmt.setInt(4, trainee.getId());
			stmt.executeUpdate();
		}
	}

	/**
	 * Получает Trainee из базы данных по его ID, используя метод получения “по
	 * именам полей”. Если Trainee с таким ID нет, возвращает null.
	 * 
	 * @param traineeId
	 * @return
	 * @throws SQLException
	 */
	public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
		String query = "SELECT * FROM trainee WHERE id=" + traineeId + ";";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.first()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int rating = rs.getInt("rating");
				return new Trainee(id, firstName, lastName, rating);
			}
		}
		return null;
	}

	/**
	 * Получает Trainee из базы данных по его ID, используя метод получения “по
	 * номерам полей”. Если Trainee с таким ID нет, возвращает null.
	 * 
	 * @param traineeId
	 * @return Trainee
	 * @throws SQLException
	 */
	public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
		String query = "SELECT * FROM trainee WHERE id=" + traineeId + ";";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return new Trainee(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(5));
			}
		}
		return null;
	}

	/**
	 * Получает все Trainee из базы данных, используя метод получения “по именам
	 * полей”. Если ни одного Trainee в БД нет, возвращает пустой список.
	 * 
	 * @return List<Trainee>
	 * @throws SQLException
	 */
	public static List<Trainee> getTraineesUsingColNames() throws SQLException {
		List<Trainee> trainee = new ArrayList<>();
		String query = "SELECT * FROM trainee;";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				trainee.add(new Trainee(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getInt("rating")));
			}
		}
		return trainee;
	}

	/**
	 * Получает все Trainee из базы данных, используя метод получения “по номерам
	 * полей”. Если ни одного Trainee в БД нет, возвращает пустой список.
	 * 
	 * @return List<Trainee>
	 * @throws SQLException
	 */
	public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
		List<Trainee> trainee = new ArrayList<>();
		String query = "SELECT * FROM trainee;";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				trainee.add(new Trainee(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(5)));
			}
		}
		return trainee;
	}

	/**
	 * Удаляет Trainee из базы данных.
	 * 
	 * @param trainee
	 * @throws SQLException
	 */
	public static void deleteTrainee(Trainee trainee) throws SQLException {
		String query = "DELETE FROM trainee WHERE id=" + trainee.getId() + ";";// "TRUNCATE TABLE trainee"
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.executeUpdate();
		}
	}

	/**
	 * Удаляет все Trainee из базы данных
	 * 
	 * @throws SQLException
	 * 
	 */
	public static void deleteTrainees() throws SQLException {
		String query = "DELETE FROM trainee;";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.executeUpdate();
		}
	}

	/**
	 * Добавляет Subject в базу данных
	 * 
	 * @param subject
	 * @throws SQLException
	 */
	public static void insertSubject(Subject subject) throws SQLException {
		String insertQuery = "INSERT INTO subject values(?,?)";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setNull(1, java.sql.Types.INTEGER);
			stmt.setString(2, subject.getName());
			stmt.executeUpdate();
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					subject.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	/**
	 * Получает Subject из базы данных по его ID, используя метод получения “по
	 * именам полей”. Если Subject с таким ID нет, возвращает null.
	 * 
	 * @param subjectId
	 * @return
	 * @throws SQLException
	 */
	public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
		String query = "SELECT * FROM subject WHERE id=" + subjectId + ";";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.first()) {
				int retId = rs.getInt("id");
				String name = rs.getString("name");
				return new Subject(retId, name);
			}
		}
		return null;
	}

	/**
	 * Получает Subject из базы данных по его ID, используя метод получения “по
	 * номерам полей”. Если Subject с таким ID нет, возвращает null.
	 * 
	 * @param subjectId
	 * @return
	 * @throws SQLException
	 */
	public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
		String query = "SELECT * FROM subject WHERE id=" + subjectId + ";";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.first()) {
				return new Subject(rs.getInt(1), rs.getString(2));
			}
		}
		return null;
	}

	/**
	 * Удаляет все Subject из базы данных.
	 * 
	 * @throws SQLException
	 * 
	 */
	public static void deleteSubjects() throws SQLException {
		String query = "DELETE FROM subject";// "TRUNCATE TABLE subject"
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.executeUpdate();
		}
	}

	/**
	 * Добавляет School в базу данных
	 * 
	 * @param school
	 * @throws SQLException
	 */
	public static void insertSchool(School school) throws SQLException {
		String insertQuery = "INSERT INTO school values(?,?,?)";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setNull(1, java.sql.Types.INTEGER);
			stmt.setString(2, school.getName());
			stmt.setInt(3, school.getYear());
			stmt.executeUpdate();
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.first()) {
					school.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	/**
	 * Получает School из базы данных по ее ID, используя метод получения “по именам
	 * полей”. Если School с таким ID нет, возвращает null.
	 * 
	 * @param schoolId
	 * @return
	 * @throws SQLException
	 */
	public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
		String query = "SELECT * FROM school WHERE id=" + schoolId + ";";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.first()) {
				return new School(rs.getInt("id"), rs.getString("name"), rs.getInt("year"));
			}
		}
		return null;
	}

	/**
	 * Получает School из базы данных по ее ID, используя метод получения “по
	 * номерам полей”. Если School с таким ID нет, возвращает null.
	 * 
	 * @param schoolId
	 * @return
	 * @throws SQLException
	 */
	public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
		String query = "SELECT * FROM school WHERE id=" + schoolId + ";";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.first()) {
				return new School(rs.getInt(1), rs.getString(2), rs.getInt(3));
			}
		}
		return null;
	}

	/**
	 * Удаляет все School из базы данных. Если список Group в School не пуст,
	 * удаляет все Group для каждой School.
	 * 
	 * @throws SQLException
	 * 
	 */
	public static void deleteSchools() throws SQLException {
		String query = "DELETE FROM school";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.executeUpdate();
		}
	}

	/**
	 * Добавляет Group в базу данных, устанавливая ее принадлежность к школе School.
	 * 
	 * @param school
	 * @param group
	 * @throws SQLException
	 */
	public static void insertGroup(School school, Group group) throws SQLException {
		String insertQuery = "INSERT INTO `group` values(?,?,?,?);";
		Connection con = JdbcUtils.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setNull(1, java.sql.Types.INTEGER);
			stmt.setInt(2, school.getId());
			stmt.setString(3, group.getName());
			stmt.setString(4, group.getRoom());
			stmt.executeUpdate();
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.first()) {
					group.setId(generatedKeys.getInt(1));
				}
			}
		}
	}

	/**
	 * Получает School по ее ID вместе со всеми ее Group из базы данных. Если School
	 * с таким ID нет, возвращает null. Метод получения (по именам или номерам
	 * полей) - на Ваше усмотрение.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static School getSchoolByIdWithGroups(int id) throws SQLException {
		String query = "SELECT school.id, school.name AS schoolName, group.name AS groupName, school.year, "
				+ "group.room, group.id AS groupId FROM school JOIN `group` ON school.id=group.schoolId WHERE school.id=" + id + ";";
		Connection con = JdbcUtils.getConnection();
		School school = new School();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			if (rs.first()) {
				school = new School(rs.getInt("id"), rs.getString("schoolName"), rs.getInt("year"));
				do {
					school.addGroup(new Group(rs.getInt("groupId"), rs.getString("groupName"), rs.getString("room")));
				} while (rs.next());
			}
		}
		return school;
	}

	/**
	 * Получает список всех School вместе со всеми их Group из базы данных. Если ни
	 * одной School в БД нет, возвращает пустой список. Метод получения (по именам
	 * или номерам полей) - на Ваше усмотрение
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<School> getSchoolsWithGroups() throws SQLException {
		String query = "SELECT school.id, school.name AS schoolName, group.name AS groupName, school.year, "
				+ "group.room, group.id AS groupId FROM school JOIN `group` ON school.id=group.schoolId;";
		Connection con = JdbcUtils.getConnection();
		List<School> schools = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			School school;
			boolean q = rs.first();
			while (q) {
				school = new School(rs.getInt("id"), rs.getString("schoolName"), rs.getInt("year"));
				while (q && (school.getId() == rs.getInt("id"))) {
					school.addGroup(new Group(rs.getInt("groupId"), rs.getString("groupName"), rs.getString("room")));
					q = rs.next();
				}
				schools.add(school);
			}
		}
		return schools;
	}
}
