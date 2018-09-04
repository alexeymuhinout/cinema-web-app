package com.rustedbrain.study.course.presenter.authentication;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.authentication.CinemaHallConstructorView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class CinemaHallConstructorViewPresenter implements CinemaHallConstructorView.ViewListener {
    private final AuthenticationService authenticationService;
    private CinemaHallConstructorView cinemaHallConstructorView;

    @Autowired
    public CinemaHallConstructorViewPresenter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
	public void setCinemaHallConstructorView(CinemaHallConstructorView cinemaHallConstructorView) {
		this.cinemaHallConstructorView = cinemaHallConstructorView;
    }

    @Override
    public void addButtonClicked(String rows, String seats) {

    }
}
