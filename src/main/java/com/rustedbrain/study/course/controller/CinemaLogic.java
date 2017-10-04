package com.rustedbrain.study.course.controller;

import com.rustedbrain.study.course.model.authorization.User;
import com.rustedbrain.study.course.model.cinema.*;

import java.util.Date;
import java.util.List;

public interface CinemaLogic {

    List<Movie> getMovies(Cinema cinema, Date date);

    List<Cinema> getCinemas(City city);

    List<City> getCities();

    List<Message> getMessages(Movie movie);

    void addFilmScreening(FilmScreening filmScreening);

    List<Seat> getAvailableSeats(Cinema cinema, FilmScreeningEvent event) throws IllegalArgumentException;

    void lockTicket(User user, FilmScreeningEvent event);

    void unlockTicket(User user, FilmScreeningEvent event);

}
