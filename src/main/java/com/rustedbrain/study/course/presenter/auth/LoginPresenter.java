package com.rustedbrain.study.course.presenter.auth;

import com.rustedbrain.study.course.service.AuthService;
import com.rustedbrain.study.course.view.auth.LoginView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class LoginPresenter implements LoginView.LoginViewListener {

    private LoginView loginView;
    private AuthService authService;

    @Autowired
    public LoginPresenter(LoginView loginView, AuthService authService) {
        this.loginView = loginView;
        this.authService = authService;

        loginView.addListener(this);
    }

    @Override
    public void loginButtonClicked(String login, String password, boolean rememberMe) {
        if (authService.login(login, password, rememberMe)) {

        } else {
            loginView.showInvalidCredentialsNotification();
        }
    }
}
