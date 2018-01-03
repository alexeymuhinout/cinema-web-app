package com.rustedbrain.study.course.view.authentication;

import com.vaadin.navigator.View;

public interface LoginView extends View {

    public void addListener(LoginViewListener listener);

    void showInvalidCredentialsNotification();

    interface LoginViewListener {
        void loginButtonClicked(String login, String password, boolean rememberMe);
    }
}
