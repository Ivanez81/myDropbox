package ru.geekbrains.dropbox.frontend.dao;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    public String id;

    public String login;
    public String pass;
    public String role;

    public User() {
    }

    public User(String login, String pass, String role) {
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
