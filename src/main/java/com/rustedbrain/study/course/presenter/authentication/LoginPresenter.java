package com.rustedbrain.study.course.presenter.authentication;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.authentication.LoginView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class LoginPresenter implements LoginView.LoginViewListener {

    private LoginView loginView;
    private AuthenticationService authenticationService;

    @Autowired
    public LoginPresenter(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        loginView.addListener(this);
    }

    @Override
    public void loginButtonClicked(String login, String password, boolean rememberMe) {
        if (authenticationService.login(login, password, rememberMe)) {

        } else {
            loginView.showInvalidCredentialsNotification();
        }
    }
}
