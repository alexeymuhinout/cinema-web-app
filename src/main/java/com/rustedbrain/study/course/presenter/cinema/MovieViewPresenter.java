package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.cinema.MovieView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;

@UIScope
@SpringComponent
public class MovieViewPresenter implements MovieView.Listener, Serializable {

    private final AuthenticationService authenticationService;
    private final CinemaService cinemaService;
    private Movie movie;
    private MovieView view;

    @Autowired
    public MovieViewPresenter(CinemaService cinemaService, AuthenticationService authenticationService) {
        this.cinemaService = cinemaService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void entered(ViewChangeListener.ViewChangeEvent event) {
        Optional<String> optionalId = Optional.ofNullable(event.getParameters());
        if (optionalId.isPresent()) {
            Optional<Movie> optionalMovie = cinemaService.getMovie(Long.parseLong(optionalId.get()));
            if (optionalMovie.isPresent()) {
                this.movie = optionalMovie.get();
                this.view.showMovieInfo(movie, authenticationService.isAuthenticated());
            } else {
                this.view.showError("Movie with specified id not exist.");
            }
        } else {
            this.view.showError("Movie id not presented.");
        }
    }


    @Override
    public void setView(MovieView view) {
        this.view = view;
    }

    @Override
    public void buttonProfileClicked(long id) {
        new PageNavigator().navigateToProfileInfoView(id);
    }

    @Override
    public void buttonCreateMessageClicked(String textArea) {
        if (authenticationService.isAuthenticated()) {
            cinemaService.createMessage(movie, authenticationService.getAuthenticUser(), textArea);
            view.reload();
        } else {
            view.showError("Only registered user can create messages");
        }
    }
}

