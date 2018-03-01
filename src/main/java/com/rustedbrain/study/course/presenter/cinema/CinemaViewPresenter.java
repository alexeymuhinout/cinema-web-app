package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.cinema.CinemaView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;
import java.util.logging.Logger;

@UIScope
@SpringComponent
public class CinemaViewPresenter implements Serializable, CinemaView.CinemaViewListener {

    private static final Logger logger = Logger.getLogger(CinemaViewPresenter.class.getName());
    private final CinemaService cinemaService;
    private final AuthenticationService authenticationService;
    private CinemaView cinemaView;

    @Autowired
    public CinemaViewPresenter(CinemaService cinemaService, AuthenticationService authenticationService) {
        this.cinemaService = cinemaService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void entered(ViewChangeListener.ViewChangeEvent event) {
        cinemaView.fillMenuPanel(authenticationService);
        Optional<String> optionalCinemaId = Optional.ofNullable(event.getParameters());
        if (optionalCinemaId.isPresent()) {
            logger.info("Cinema id parameter available. Retrieving cinema by id...");
            Long cinemaId = Long.parseLong(optionalCinemaId.get());
            Cinema cinema = cinemaService.getCinema(cinemaId);
            cinemaView.fillCinemaPanel(cinema, authenticationService.getUserRole());
        } else {
            logger.warning("Cinema id not available. Navigating to cinemas view...");
        }
    }

    @Override
    public void setView(CinemaView cinemaView) {
        this.cinemaView = cinemaView;
    }

    @Override
    public void buttonFilmViewTimeClicked(long id) {
        new PageNavigator().navigateToCinemaHallView(id);
    }

    @Override
    public void buttonDeleteCinemaClicked(long id) {
        this.cinemaService.deleteCinema(id);
        this.cinemaView.reloadPage();
    }
}
