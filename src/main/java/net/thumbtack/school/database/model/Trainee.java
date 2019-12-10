package net.thumbtack.school.database.model;

public class Trainee {

	/**
	 * id для Trainee. Для несохраненного в БД Trainee это поле имеет значение 0,
	 * после сохранения значение присваивается БД
	 */
	private int id;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + rating;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainee other = (Trainee) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (rating != other.rating)
			return false;
		return true;
	}

}
