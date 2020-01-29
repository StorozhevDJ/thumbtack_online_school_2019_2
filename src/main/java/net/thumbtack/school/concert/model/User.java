package net.thumbtack.school.concert.model;

import java.util.Objects;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public User() {

    }

    public User(String firstName, String lastName, String login, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setLogin(login);
        setPassword(password);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, login, password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
