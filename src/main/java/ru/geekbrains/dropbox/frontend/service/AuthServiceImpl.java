package ru.geekbrains.dropbox.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public boolean isAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.isAuthenticated();
    }

    @Override
    public void login(String login, String pass) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login, pass);
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
