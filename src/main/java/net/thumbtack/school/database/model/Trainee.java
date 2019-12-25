package net.thumbtack.school.database.model;

import java.util.Objects;

public class Trainee {

	/**
	 * id для Trainee. Для несохраненного в БД Trainee это поле имеет значение 0,
	 * после сохранения значение присваивается БД
	 */
	private int id = 0;
	/**
	 * Имя студента
	 */
	private String firstName;
	/**
	 * Фамилия студента
	 */
	private String lastName;
	/**
	 * Оценка студента
	 */
	private int rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Конструктор без параметров с пустым телом. На этом занятии он нам не
	 * понадобится, но будет нужен на следующем занятии, поэтому лучше его сразу
	 * сделать.
	 */
	public Trainee() {

	}

	/**
	 * Конструктор, устанавливающий значения всех полей
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param rating
	 */
	public Trainee(int id, String firstName, String lastName, int rating) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setRating(rating);
	}

	/**
	 * Конструктор, устанавливающий значения всех полей , Полю id присваивается
	 * значение 0.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param rating
	 */
	public Trainee(String firstName, String lastName, int rating) {
		this(0, firstName, lastName, rating);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Trainee)) return false;
		Trainee trainee = (Trainee) o;
		return getId() == trainee.getId() &&
				getRating() == trainee.getRating() &&
				Objects.equals(getFirstName(), trainee.getFirstName()) &&
				Objects.equals(getLastName(), trainee.getLastName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getRating());
	}
}
