package com.rustedbrain.study.course.view.auth;

import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

@SpringView(name = VaadinUI.LOGIN_VIEW)
public class LoginViewImpl extends VerticalLayout implements LoginView {

    public static final String LOGGED_USER_ATTRIBUTE = "user";
    public static final String LOGGED_ADMINISTRATOR_ATTRIBUTE = "administrator";

    private List<LoginViewListener> loginViewListeners = new ArrayList<>();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Panel panel = new Panel("Login");
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        TextField loginTextField = new TextField("Login");
        content.addComponent(loginTextField);
        TextField passwordTextField = new PasswordField("Password");
        content.addComponent(passwordTextField);

        Button send = new Button("Enter", (Button.ClickListener) event1 -> loginViewListeners.forEach(loginViewListener -> loginViewListener.loginButtonClicked(loginTextField.getValue(), passwordTextField.getValue())));

        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void addListener(LoginViewListener listener) {
        loginViewListeners.add(listener);
    }

    @Override
    public void showInvalidCredentialsNotification() {
        Notification.show("Invalid authorization data specified", Notification.Type.ERROR_MESSAGE);
    }
}