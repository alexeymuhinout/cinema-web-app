package com.rustedbrain.study.course.presenter.authentication;

import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.authentication.RegistrationView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.logging.Logger;

@UIScope
@SpringComponent
public class RegistrationPresenter implements Serializable, RegistrationView.ViewListener {

    private static final Logger logger = Logger.getLogger(RegistrationPresenter.class.getName());
    private final CinemaService cinemaService;

    private RegistrationView view;

    @Autowired
    public RegistrationPresenter(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void entered(ViewChangeListener.ViewChangeEvent event) {
        view.voidShowRegistrationPanel(cinemaService.getCities());
    }

    @Override
    public void buttonRegisterClicked(String login, String password, String name, String surname, City city, LocalDate birthday, String mail) {
        checkLoginPasswordMailEmptiness(login, password, mail);
        try {
            cinemaService.registerMember(login, password, name, surname, city.getId(), birthday, mail);
            new PageNavigator().navigateToMainView();
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }

    private void checkLoginPasswordMailEmptiness(String login, String password, String mail) {
        if (login == null || login.isEmpty()) {
            view.showError("Login can not be empty.");
        } else if (password == null || password.isEmpty()) {
            view.showError("Password can not be empty.");
        } else if (mail == null || mail.isEmpty()) {
            view.showError("Mail can not be empty.");
        }
    }

    @Override
    public void setView(RegistrationView view) {
        this.view = view;
    }
}
