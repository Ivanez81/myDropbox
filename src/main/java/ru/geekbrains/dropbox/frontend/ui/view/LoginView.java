package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.geekbrains.dropbox.frontend.service.FilesService;


@SpringView(name = "login")
public class LoginView extends VerticalLayout implements View {

    @Autowired
    private FilesService filesService;

    public LoginView() {
        Panel authPanel = new Panel("Login");
        authPanel.setSizeUndefined();
        addComponent(authPanel);
        FormLayout authContent = new FormLayout();

        TextField txtUser = new TextField("Username");
        txtUser.focus();
        authContent.addComponent(txtUser);

        PasswordField txtPassword = new PasswordField("Password");
        authContent.addComponent(txtPassword);

        Notification notifFailed = new Notification("Authentication failed!");
        notifFailed.setDelayMsec(2000);
        notifFailed.setPosition(Position.TOP_LEFT);

        Button btnLogin = new Button("Login", clickEvent -> {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    txtUser.getValue(),
                    txtPassword.getValue()
            );

            SecurityContextHolder.getContext().setAuthentication(token);
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                getUI().getNavigator().navigateTo("");
            } else {
                notifFailed.show(Page.getCurrent());
            }

        });
        authContent.addComponent(btnLogin);
        authContent.setSizeUndefined();
        authContent.setMargin(true);
        authPanel.setContent(authContent);
        setComponentAlignment(authPanel, Alignment.TOP_LEFT);
    }
}
