package net.thumbtack.school.ttschool;

import java.io.Serializable;

public class Trainee implements Serializable {

	private static final long serialVersionUID = 8626523001988171067L;

	private String firstName, lastName;
	private int rating;

	/**
	 * Создает Trainee с указанными значениями полей. Для недопустимых значений
	 * входных параметров выбрасывает TrainingException с соответствующим
	 * TrainingErrorCode
	 *
	 * @param
	 * @return
	 */
	public Trainee(String firstName, String lastName, int rating) throws TrainingException {
		setFirstName(firstName);
		setLastName(lastName);
		setRating(rating);
	}

	/**
	 * Возвращает имя учащегося
	 *
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Устанавливает имя учащегося. Для недопустимого значения входного параметров
	 * выбрасывает TrainingException с TrainingErrorCode.TRAINEE_WRONG_FIRSTNAME
	 *
	 * @param firstName
	 * @throws TrainingException
	 */
	public void setFirstName(String firstName) throws TrainingException {
		if (firstName == null || firstName.isEmpty()) {
			throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_FIRSTNAME);
		}
		this.firstName = firstName;
	}

	/**
	 * Возвращает фамилию учащегося
	 *
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Устанавливает фамилию учащегося. Для недопустимого значения входного
	 * параметров выбрасывает TrainingException с
	 * TrainingErrorCode.TRAINEE_WRONG_LASTNAME
	 *
	 * @param lastName
	 * @throws TrainingException
	 */
	public void setLastName(String lastName) throws TrainingException {
		if (lastName == null || lastName.isEmpty()) {
			throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_LASTNAME);
		}
		this.lastName = lastName;
	}

	/**
	 * Возвращает оценку учащегося
	 *
	 * @return
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Устанавливает оценку учащегося. Для недопустимого значения входного
	 * параметров выбрасывает TrainingException с
	 * TrainingErrorCode.TRAINEE_WRONG_RATING
	 *
	 * @param rating
	 * @throws TrainingException
	 */
	public void setRating(int rating) throws TrainingException {
		if ((rating < 1) || (rating > 5)) {
			throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_RATING);
		}
		this.rating = rating;
	}

	/**
	 * Возвращает полное имя учащегося (имя и фамилию, разделенные пробелом)
	 *
	 * @return
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
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
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (rating != other.rating)
			return false;
		return true;
	}

	/**
	 * Методы equals и hashCode
	 */

}
