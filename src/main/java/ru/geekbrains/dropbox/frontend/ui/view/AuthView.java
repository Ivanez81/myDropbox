package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.dropbox.frontend.dao.User;
import ru.geekbrains.dropbox.frontend.repository.UserRepository;
import ru.geekbrains.dropbox.frontend.service.AuthService;

@SpringView(name = "auth")
public class AuthView extends VerticalLayout implements View {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Panel authPanel = new Panel("Create your login and password");
        authPanel.setSizeUndefined();
        addComponent(authPanel);
        FormLayout authContent = new FormLayout();

        TextField txtUser = new TextField("Login");
        txtUser.focus();
        authContent.addComponent(txtUser);

        PasswordField txtPassword = new PasswordField("Password");
        authContent.addComponent(txtPassword);

        Button btnCreate = new Button("Create", clickEvent -> {
            if (userRepository.findByLogin(txtUser.getValue()) != null) {
                Notification.show("This name already used").setDelayMsec(2000);
            } else {
                userRepository.save(new User(txtUser.getValue(), passwordEncoder.encode(txtPassword.getValue()), "USER"));
                authService.login(txtUser.getValue(), txtPassword.getValue());
                getUI().getNavigator().navigateTo("");
            }
        });

        authContent.addComponent(btnCreate);
        authContent.setSizeUndefined();
        authContent.setMargin(true);
        authPanel.setContent(authContent);
        setComponentAlignment(authPanel, Alignment.TOP_LEFT);
    }



}
