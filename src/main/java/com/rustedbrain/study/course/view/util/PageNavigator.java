package com.rustedbrain.study.course.view.util;

import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.model.cinema.Movie;
import com.rustedbrain.study.course.model.cinema.Seat;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.cinema.CinemaHallViewImpl;
import com.rustedbrain.study.course.view.cinema.CinemaViewImpl;
import com.rustedbrain.study.course.view.cinema.CityViewImpl;
import com.rustedbrain.study.course.view.cinema.MovieViewImpl;
import com.vaadin.server.VaadinSession;

import java.util.Set;

public class PageNavigator {

    public void navigateToCinemaView(Cinema cinema, String message) {
        NotificationUtil.setMessage(message);
        VaadinSession.getCurrent().setAttribute(CinemaViewImpl.CINEMA_ATTRIBUTE, cinema);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CINEMA_VIEW);
    }

    public void navigateToCinemaView(Cinema cinema) {
        NotificationUtil.clearMessage();
        VaadinSession.getCurrent().setAttribute(CinemaViewImpl.CINEMA_ATTRIBUTE, cinema);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CINEMA_VIEW);
    }

    public void navigateToCityView(City city, String message) {
        NotificationUtil.setMessage(message);
        VaadinSession.getCurrent().setAttribute(CityViewImpl.CITY_ATTRIBUTE, city);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITY_VIEW);
    }

    public void navigateToCityView(City city) {
        NotificationUtil.clearMessage();
        VaadinSession.getCurrent().setAttribute(CityViewImpl.CITY_ATTRIBUTE, city);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITY_VIEW);
    }

    public void navigateToMovieView(Movie movie, String message) {
        NotificationUtil.setMessage(message);
        VaadinSession.getCurrent().setAttribute(MovieViewImpl.MOVIE_ATTRIBUTE, movie);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MOVIE_VIEW);
    }

    public void navigateToMovieView(Movie movie) {
        NotificationUtil.clearMessage();
        VaadinSession.getCurrent().setAttribute(MovieViewImpl.MOVIE_ATTRIBUTE, movie);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MOVIE_VIEW);
    }

    public void navigateToFilmScreeningTicketView(Long id, String message) {
        NotificationUtil.setMessage(message);
        VaadinSession.getCurrent().setAttribute(CinemaHallViewImpl.FILM_SCREENING_EVENT_ID_ATTRIBUTE, id);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CINEMA_HALL_VIEW);
    }

    public void navigateToFilmScreeningTicketView(Long id) {
        NotificationUtil.clearMessage();
        VaadinSession.getCurrent().setAttribute(CinemaHallViewImpl.FILM_SCREENING_EVENT_ID_ATTRIBUTE, id);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CINEMA_HALL_VIEW);
    }

    public void navigateToFilmScreeningTicketUserInfo(Long id, Set<Seat> selectedSeats) {
        VaadinSession.getCurrent().setAttribute(CinemaHallViewImpl.FILM_SCREENING_EVENT_ID_ATTRIBUTE, id);
        VaadinSession.getCurrent().setAttribute(CinemaHallViewImpl.SELECTED_SEATS_ATTRIBUTE, selectedSeats);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.TICKET_USER_INFO_VIEW);
    }

    public void navigateToMainView(String message) {
        NotificationUtil.setMessage(message);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }

    public void navigateToMainView() {
        NotificationUtil.clearMessage();
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }

    public void navigateToCitiesView() {

    }
}
