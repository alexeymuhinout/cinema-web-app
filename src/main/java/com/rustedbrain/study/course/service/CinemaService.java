package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.authorization.User;
import com.rustedbrain.study.course.model.cinema.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CinemaService {

    List<Movie> getCurrentMovies(Cinema cinema, Date date);

    List<Cinema> getCinemas(City city);

    List<City> getCities();

    List<Comment> getMessages(Movie movie);

    void addFilmScreening(FilmScreening filmScreening);

    List<Seat> getAvailableSeats(Cinema cinema, FilmScreeningEvent event) throws IllegalArgumentException;

    void lockTicket(User user, FilmScreeningEvent event);

    void unlockTicket(User user, FilmScreeningEvent event);

    List<Cinema> getCinemas();


    boolean isCinemaExist(String name);

    Cinema getCinema(String name);

    void deleteCity(String city);

    void createCity(String name);

    void deleteCinema(Cinema cinema);

    void createCinema(City city, String name, String street);

    Cinema getCinema(Long cinemaId);

    FilmScreeningEvent getFilmScreeningEvent(Long filmScreeningEventId);

    void buyTickets(List<Ticket> boughtTickets);

    List<Movie> getCurrentMovies();

    Optional<City> getCityByName(String ipAddress) throws IOException;

    Optional<Cinema> getNearestCinema(float latitude, float longitude) throws IOException;

    Optional<City> getCityByInetAddress(InetAddress address) throws IOException;
}
