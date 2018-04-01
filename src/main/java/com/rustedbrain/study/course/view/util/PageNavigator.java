package com.rustedbrain.study.course.view.util;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.model.persistence.cinema.Seat;
import com.rustedbrain.study.course.presenter.cinema.TicketBuyingViewPresenter;
import com.rustedbrain.study.course.presenter.cinema.TicketsInfoViewPresenter;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.cinema.MovieViewImpl;
import com.vaadin.server.VaadinSession;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PageNavigator {

    public static final String PARAM_VALUES_SEPARATOR = "&";
    private static final String PARAMS_SEPARATOR = "/";
    private static final String KEY_VALUE_SEPARATOR = "=";

    public void navigateToHelpView(String message) {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.HELP_VIEW);
    }

    public void navigateToHelpView() {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.HELP_VIEW);
    }

    public void navigateToCinemaView(Cinema cinema, String message) {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CINEMA_VIEW);
    }

    public void navigateToCinemaView(long cinemaId) {
        navigateTo(VaadinUI.CINEMA_VIEW + PARAMS_SEPARATOR + cinemaId);
    }

    private void navigateTo(String path) {
        VaadinUI.getCurrent().getNavigator().navigateTo(path);
    }


    public void navigateToCityView(long cityId) {
        navigateTo(VaadinUI.CITY_VIEW + PARAMS_SEPARATOR + cityId);
    }

    public void navigateToMovieView(Movie movie, String message) {
        VaadinSession.getCurrent().setAttribute(MovieViewImpl.MOVIE_ATTRIBUTE, movie);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MOVIE_VIEW);
    }

    public void navigateToMovieView(Movie movie) {
        VaadinSession.getCurrent().setAttribute(MovieViewImpl.MOVIE_ATTRIBUTE, movie);
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MOVIE_VIEW);
    }

    public void navigateToCinemaHallView(Long id, String message) {
        navigateTo(VaadinUI.CINEMA_HALL_VIEW + PARAMS_SEPARATOR + id);
    }

    public void navigateToCinemaHallView(long FilmScreeningEventId) {
        navigateTo(VaadinUI.CINEMA_HALL_VIEW + PARAMS_SEPARATOR + FilmScreeningEventId);
    }

    public void navigateToFilmScreeningTicketUserInfo(Long id, Set<Seat> selectedSeats) {
        StringBuilder selectedSeatParamBuilder = new StringBuilder(PARAMS_SEPARATOR + id.toString());
        for (Seat seat : selectedSeats) {
            selectedSeatParamBuilder.append(PARAMS_SEPARATOR).append(seat);
        }
        navigateTo(VaadinUI.TICKET_BUYING_VIEW + selectedSeatParamBuilder.toString());
    }

    public void navigateToMainView(String message) {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }

    public void navigateToMainView() {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.MAIN_VIEW);
    }

    public void navigateToCitiesView(String message) {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITIES_VIEW);
    }

    public void navigateToCitiesView() {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITIES_VIEW);
    }

    public void navigateToCityCinemasView(long cityId) {
        VaadinUI.getCurrent().getNavigator().navigateTo(VaadinUI.CITY_CINEMAS_VIEW + "/" + cityId);
    }

    public void navigateToTicketBuyingView(long filmScreeningEventId, long... seatIds) {
        String seatdIdsCommaSeparated = LongStream.of(seatIds)
                .mapToObj(Long::toString)
                .collect(Collectors.joining(TicketBuyingViewPresenter.PARAM_SEPARATOR));

        navigateTo(VaadinUI.TICKET_BUYING_VIEW + PARAMS_SEPARATOR + TicketBuyingViewPresenter.EVENT_ID_PARAM_KEY + KEY_VALUE_SEPARATOR + filmScreeningEventId + PARAM_VALUES_SEPARATOR + TicketBuyingViewPresenter.SEATS_ID_PARAM_KEY + KEY_VALUE_SEPARATOR + seatdIdsCommaSeparated);
    }

    public void navigateToTicketsInfoView(long... ticketsIds) {
        String ticketsIdsCommaSeparated = LongStream.of(ticketsIds)
                .mapToObj(Long::toString)
                .collect(Collectors.joining(TicketBuyingViewPresenter.PARAM_SEPARATOR));

        navigateTo(VaadinUI.TICKET_INFO_VIEW + PARAMS_SEPARATOR + TicketsInfoViewPresenter.TICKETS_ID_PARAM_KEY + KEY_VALUE_SEPARATOR + ticketsIdsCommaSeparated);
    }
}
