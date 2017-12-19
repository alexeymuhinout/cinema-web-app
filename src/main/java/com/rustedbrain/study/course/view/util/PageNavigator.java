package com.rustedbrain.study.course.view.util;

import com.rustedbrain.study.course.model.cinema.Seat;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.cinema.CinemaHallView;
import com.rustedbrain.study.course.view.cinema.CinemaView;
import com.rustedbrain.study.course.view.cinema.CityView;
import com.rustedbrain.study.course.view.cinema.MovieView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import java.util.Set;

public class PageNavigator {

    public void navigateToCinemaView(UI ui, Long id) {
        if (id != null) {
            VaadinSession.getCurrent().setAttribute(CinemaView.CINEMA_ID_ATTRIBUTE, id);
            ui.getNavigator().navigateTo(VaadinUI.CINEMA_VIEW);
        }
    }

    public void navigateToCityView(UI ui, Long id) {
        if (id != null) {
            VaadinSession.getCurrent().setAttribute(CityView.CITY_ID_ATTRIBUTE, id);
            ui.getNavigator().navigateTo(VaadinUI.CITY_VIEW);
        }
    }

    public void navigateToMovieView(UI ui, Long id) {
        if (id != null) {
            VaadinSession.getCurrent().setAttribute(MovieView.MOVIE_ID_ATTRIBUTE, id);
            ui.getNavigator().navigateTo(VaadinUI.MOVIE_VIEW);
        }
    }

    public void navigateToTicketView(UI ui, Long id) {
        if (id != null) {
            VaadinSession.getCurrent().setAttribute(CinemaHallView.FILM_SCREENING_EVENT_ID_ATTRIBUTE, id);
            ui.getNavigator().navigateTo(VaadinUI.CINEMA_HALL_VIEW);
        }
    }

    public void navigateToTicketUserInfo(UI ui, Long id, Set<Seat> selectedSeats) {
        if (id != null && selectedSeats != null && !selectedSeats.isEmpty()) {
            VaadinSession.getCurrent().setAttribute(CinemaHallView.FILM_SCREENING_EVENT_ID_ATTRIBUTE, id);
            VaadinSession.getCurrent().setAttribute(CinemaHallView.SELECTED_SEATS_ATTRIBUTE, selectedSeats);
            ui.getNavigator().navigateTo(VaadinUI.TICKET_USER_INFO_VIEW);
        }
    }

    public void navigateToMainView(UI ui) {
        ui.getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }
}
