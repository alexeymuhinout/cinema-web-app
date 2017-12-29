package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.cinema.Seat;

import java.util.List;

public interface CinemaHallView {

    void displaySelectedSeats(List<Seat> seats);

    void addListener(CinemaHallViewListener cinemaHallViewListener);

    public interface CinemaHallViewListener {

    }
}
