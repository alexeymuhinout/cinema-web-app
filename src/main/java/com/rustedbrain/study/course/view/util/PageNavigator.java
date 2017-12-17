package com.rustedbrain.study.course.view.util;

import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.Movie;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.cinema.CinemaView;
import com.rustedbrain.study.course.view.cinema.CityView;
import com.rustedbrain.study.course.view.cinema.MovieView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class PageNavigator {

    public void navigateToCinemaView(UI ui, Cinema cinema) {
        if (cinema != null) {
            VaadinSession.getCurrent().setAttribute(CinemaView.CINEMA_ATTRIBUTE, cinema);
            ui.getNavigator().navigateTo(VaadinUI.CINEMA_VIEW);
        }
    }

    public void navigateToCityView(UI ui, String cityName) {
        if (cityName != null && !cityName.isEmpty()) {
            VaadinSession.getCurrent().setAttribute(CityView.CITY_ATTRIBUTE, cityName);
            ui.getNavigator().navigateTo(VaadinUI.CITY_VIEW);
        }
    }

    public void navigateToMovieView(UI ui, Movie movie) {
        if (movie != null) {
            VaadinSession.getCurrent().setAttribute(MovieView.MOVIE_ATTRIBUTE, movie);
            ui.getNavigator().navigateTo(VaadinUI.MOVIE_VIEW);
        }
    }

}
