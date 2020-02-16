package net.thumbtack.school.concert.model;

import java.util.Objects;

public class Session {

    private String login;
    private String token;

    public Session() {

    }

    public Session(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(login, session.login) &&
                Objects.equals(token, session.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, token);
    }
}
