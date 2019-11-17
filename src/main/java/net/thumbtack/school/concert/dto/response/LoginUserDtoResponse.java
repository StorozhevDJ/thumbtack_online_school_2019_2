package net.thumbtack.school.concert.dto.response;

public class LoginUserDtoResponse {

    public LoginUserDtoResponse(String token) {
        setToken(token);
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
