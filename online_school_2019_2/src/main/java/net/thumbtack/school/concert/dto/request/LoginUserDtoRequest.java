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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        LoginUserDtoRequest other = (LoginUserDtoRequest) obj;
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
