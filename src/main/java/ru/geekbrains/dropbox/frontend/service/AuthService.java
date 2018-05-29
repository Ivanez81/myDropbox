package ru.geekbrains.dropbox.frontend.service;

public interface AuthService {
    boolean isAuth();

    void login(String login, String pass);

    void logout();
}
