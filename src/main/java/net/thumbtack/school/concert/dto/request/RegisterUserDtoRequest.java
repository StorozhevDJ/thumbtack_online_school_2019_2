package net.thumbtack.school.concert.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterUserDtoRequest {

	@NotBlank(message = "Имя пользователя не задано. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]{1,20}$", message = "Имя может содержать только заглавные и строчные латинские и кириллические буквы и быть длинной от 1 до 20 символов. ")
	private String firstName;
	@NotBlank(message = "Фамилия пользователя не задана. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]{1,20}$", message = "Фамилия может содержать только заглавные и строчные латинские и кириллические буквы и быть длинной от 1 до 20 символов. ")
	private String lastName;
	@NotBlank(message = "Логин не задан. ")
	@Pattern(regexp = "^[a-zA-Z0-9-_\\.]{1,20}$", message = "Логин может содержать только латинские буквы, цифры и быть длинной от 1 до 20 символов. ")
	private String login;
	@NotBlank(message = "Пароль не задан. ")
	@Size(min = 4, max = 20, message = "Пароль должен быть длинной от 4 до 20 символов. ")
	private String password;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		RegisterUserDtoRequest other = (RegisterUserDtoRequest) obj;
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
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	
}
