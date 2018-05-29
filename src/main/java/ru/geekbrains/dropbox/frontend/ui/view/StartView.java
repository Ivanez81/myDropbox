package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView(name = "start")
public class StartView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


        Panel authPanel = new Panel("Sing In / Sign Up");
        authPanel.setSizeUndefined();
        addComponent(authPanel);
        FormLayout authContent = new FormLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button btnSignIn = new Button("Sign In", clickEvent -> {
            getUI().getNavigator().navigateTo("login");
        });

        Button btnSignUp = new Button("Sign Up", clickEvent -> {
            getUI().getNavigator().navigateTo("auth");
        });

        Label label = new Label(" or ");

        horizontalLayout.addComponents(btnSignIn, label, btnSignUp);

        authContent.addComponent(horizontalLayout);

        authContent.setSizeUndefined();
        authContent.setMargin(true);
        authPanel.setContent(authContent);
        setComponentAlignment(authPanel, Alignment.TOP_LEFT);
    }
}
