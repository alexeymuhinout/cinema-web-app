package com.rustedbrain.study.course.presenter.authentication;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.authentication.RegistrationViewImpl;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class RegistrationPresenter {

    private RegistrationViewImpl registrationViewImpl;
    private AuthenticationService authenticationService;

    @Autowired
    public RegistrationPresenter(RegistrationViewImpl registrationViewImpl, AuthenticationService authenticationService) {
        this.registrationViewImpl = registrationViewImpl;
        this.authenticationService = authenticationService;
    }
}
