package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.ApplicationView;
import com.vaadin.navigator.ViewChangeListener;
import org.springframework.beans.factory.annotation.Autowired;

public interface CinemaView extends ApplicationView {

    @Autowired
    void addCinemaViewListener(CinemaViewListener listener);

    void fillCinemaPanel(Cinema cinema, UserRole role);

    void fillMenuPanel(AuthenticationService authenticationService);

    interface CinemaViewListener {

        void entered(ViewChangeListener.ViewChangeEvent event);

        void setView(CinemaView cinemaView);

        void buttonFilmViewTimeClicked(long id);

        void buttonDeleteCinemaClicked(long id);
    }
}
