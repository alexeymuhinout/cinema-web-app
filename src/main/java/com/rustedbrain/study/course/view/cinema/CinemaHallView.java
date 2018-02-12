package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.FilmScreeningEvent;
import com.rustedbrain.study.course.model.persistence.cinema.Seat;
import com.rustedbrain.study.course.view.ApplicationView;
import com.vaadin.navigator.ViewChangeListener;

import java.util.List;

public interface CinemaHallView extends ApplicationView {

    void displaySelectedSeats(List<Seat> seats);

    void addListener(CinemaHallViewListener cinemaHallViewListener);

    void fillFilmScreeningEventPanel(FilmScreeningEvent filmScreeningEvent);

    void unsetSeatSelected(Seat seat);

    void setSeatSelected(Seat seat);

    interface CinemaHallViewListener {

        void fireSeatSelected(Seat seat);

        void setView(CinemaHallView cinemaHallView);

        void entered(ViewChangeListener.ViewChangeEvent event);

        void buttonBuyTicketClicked();
    }
}
