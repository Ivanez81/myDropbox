package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.dropbox.frontend.dao.Users;
import ru.geekbrains.dropbox.frontend.repository.UsersRepository;

@SpringView(name = "auth")
public class AuthView extends VerticalLayout implements View {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Panel authPanel = new Panel("Create your login and password");
        authPanel.setSizeUndefined();
        addComponent(authPanel);
        FormLayout authContent = new FormLayout();

        TextField txtUser = new TextField("Username");
        txtUser.focus();
        authContent.addComponent(txtUser);

        PasswordField txtPassword = new PasswordField("Password");
        authContent.addComponent(txtPassword);

        Button btnCreate = new Button("Create", clickEvent -> {
            if (usersRepository.findByLogin(txtUser.getValue()) != null) {
                Notification.show("This name already used");
            } else {
                usersRepository.save(new Users(txtUser.getValue(), passwordEncoder.encode(txtPassword.getValue()), "USER"));
            }
        });

        authContent.addComponent(btnCreate);
        authContent.setSizeUndefined();
        authContent.setMargin(true);
        authPanel.setContent(authContent);
        setComponentAlignment(authPanel, Alignment.TOP_LEFT);
    }



}
