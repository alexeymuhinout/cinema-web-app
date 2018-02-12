package com.rustedbrain.study.course.view.util;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.model.persistence.cinema.Seat;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.cinema.CityViewImpl;
import com.rustedbrain.study.course.view.cinema.MovieViewImpl;
import com.vaadin.server.VaadinSession;

import java.util.Set;

public class PageNavigator {

    private static final String URL_PARAM_SEPARATOR = "/";

    public void navigateToHelpView(String message) {
        NotificationUtil.setMessage(message);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.HELP_VIEW);
    }

    public void navigateToHelpView() {
        NotificationUtil.clearMessage();
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.HELP_VIEW);
    }

    public void navigateToCinemaView(Cinema cinema, String message) {
        NotificationUtil.setMessage(message);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CINEMA_VIEW);
    }

    public void navigateToCinemaView(long cinemaId) {
        NotificationUtil.clearMessage();
        navigateTo(VaadinUI.CINEMA_VIEW + URL_PARAM_SEPARATOR + cinemaId);
    }

    private void navigateTo(String path) {
        VaadinUI.getCurrent().getNavigator().navigateTo(path);
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

    public void navigateToCinemaHallView(Long id, String message) {
        NotificationUtil.setMessage(message);
        navigateTo(VaadinUI.CINEMA_HALL_VIEW + URL_PARAM_SEPARATOR + id);
    }

    public void navigateToCinemaHallView(long FilmScreeningEventId) {
        NotificationUtil.clearMessage();
        navigateTo(VaadinUI.CINEMA_HALL_VIEW + URL_PARAM_SEPARATOR + FilmScreeningEventId);
    }

    public void navigateToFilmScreeningTicketUserInfo(Long id, Set<Seat> selectedSeats) {
        StringBuilder selectedSeatParamBuilder = new StringBuilder(URL_PARAM_SEPARATOR + id.toString());
        for (Seat seat : selectedSeats) {
            selectedSeatParamBuilder.append(URL_PARAM_SEPARATOR).append(seat);
        }
        navigateTo(VaadinUI.TICKET_USER_INFO_VIEW + selectedSeatParamBuilder.toString());
    }

    public void navigateToMainView(String message) {
        NotificationUtil.setMessage(message);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }

    public void navigateToMainView() {
        NotificationUtil.clearMessage();
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }

    public void navigateToCitiesView(String message) {
        NotificationUtil.setMessage(message);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITIES_VIEW);
    }

    public void navigateToCitiesView() {
        NotificationUtil.clearMessage();
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITIES_VIEW);
    }

    public void navigateToCityCinemasView(long cityId) {
        NotificationUtil.clearMessage();
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITY_CINEMAS_VIEW + "/" + cityId);
    }
}
