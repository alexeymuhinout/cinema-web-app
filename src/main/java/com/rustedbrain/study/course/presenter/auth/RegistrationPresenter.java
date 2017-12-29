package com.rustedbrain.study.course.presenter.auth;

import com.rustedbrain.study.course.service.AuthService;
import com.rustedbrain.study.course.view.auth.RegistrationView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class RegistrationPresenter {

    private RegistrationView registrationView;
    private AuthService authService;

    @Autowired
    public RegistrationPresenter(RegistrationView registrationView, AuthService authService) {
        this.registrationView = registrationView;
        this.authService = authService;
    }
}
