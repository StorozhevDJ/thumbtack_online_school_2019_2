package net.thumbtack.school.concert.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginUserDtoRequest {

	@NotBlank(message = "Логин не задан. ")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 -_\\.]{1,20}$", message = "Логин может содержать только латинские буквы, цифры и быть длинной от 1 до 20 символов. ")
	private String login;
	@NotBlank(message = "Пароль не задан. ")
	@Size(min = 4, max = 20, message = "Пароль должен быть длинной от 4 до 20 символов. ")
	private String password;

	public LoginUserDtoRequest() {
	}

	public LoginUserDtoRequest(String login, String password) {
		setLogin(login);
		setPassword(password);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
