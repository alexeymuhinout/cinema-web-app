package com.rustedbrain.study.course.presenter.authentication;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.authentication.RegistrationView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class RegistrationPresenter {

    private RegistrationView registrationView;
    private AuthenticationService authenticationService;

    @Autowired
    public RegistrationPresenter(RegistrationView registrationView, AuthenticationService authenticationService) {
        this.registrationView = registrationView;
        this.authenticationService = authenticationService;
    }
}
