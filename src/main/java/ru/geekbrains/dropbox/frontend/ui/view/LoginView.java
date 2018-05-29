package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.dropbox.frontend.repository.UserRepository;
import ru.geekbrains.dropbox.frontend.service.AuthService;
import ru.geekbrains.dropbox.frontend.service.FilesService;


@SpringView(name = "login")
public class LoginView extends VerticalLayout implements View {

    @Autowired
    private FilesService filesService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Panel authPanel = new Panel("Login");
        authPanel.setSizeUndefined();
        addComponent(authPanel);
        FormLayout authContent = new FormLayout();

        TextField txtUser = new TextField("Username");
        txtUser.focus();
        authContent.addComponent(txtUser);

        PasswordField txtPassword = new PasswordField("Password");
        authContent.addComponent(txtPassword);

        Notification notificationFailed = new Notification("Authentication failed!");
        notificationFailed.setDelayMsec(2000);
        notificationFailed.setPosition(Position.TOP_LEFT);

        Button btnLogin = new Button("Login", clickEvent -> {
            try {
                authService.login(txtUser.getValue(), txtPassword.getValue());
                getUI().getNavigator().navigateTo("");
            } catch (BadCredentialsException bce) {
                Notification.show("Wrong user name or password!").setDelayMsec(2000);
            }
        });
        authContent.addComponent(btnLogin);

        authContent.setSizeUndefined();
        authContent.setMargin(true);
        authPanel.setContent(authContent);
        setComponentAlignment(authPanel, Alignment.TOP_LEFT);
    }

}
