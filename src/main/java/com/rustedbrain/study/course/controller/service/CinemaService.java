package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.model.authorization.User;
import com.rustedbrain.study.course.model.cinema.*;

import java.util.Date;
import java.util.List;

public interface CinemaService {

    List<Movie> getMovies(Cinema cinema, Date date);

    List<Cinema> getCinemas(City city);

    List<City> getCities();

    List<Comment> getMessages(Movie movie);

    void addFilmScreening(FilmScreening filmScreening);

    List<Seat> getAvailableSeats(Cinema cinema, FilmScreeningEvent event) throws IllegalArgumentException;

    void lockTicket(User user, FilmScreeningEvent event);

    void unlockTicket(User user, FilmScreeningEvent event);

    List<Cinema> getCinemas();

    boolean isCityExist(String name);

    boolean isCinemaExist(String name);

    City getCity(String name);

    Cinema getCinema(String name);

    void deleteCity(City city);

    void createCity(String name);

    void deleteCinema(Cinema cinema);

    void createCinema(City city, String name, String street);
}
