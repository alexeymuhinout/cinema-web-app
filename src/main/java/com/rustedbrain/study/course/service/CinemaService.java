package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.exception.ResourceException;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public interface CinemaService {

    List<Movie> getCurrentMovies(Cinema cinema, Date date);

    Set<Cinema> getCityCinemas(City city);

    List<City> getCities();

    Page<City> getCitiesPage(int page, int pageSize);

    List<Comment> getMessages(Movie movie);

    void addFilmScreening(FilmScreening filmScreening);

    List<Seat> getAvailableSeats(Cinema cinema, FilmScreeningEvent event) throws IllegalArgumentException;

    void lockTicket(User user, FilmScreeningEvent event);

    void unlockTicket(User user, FilmScreeningEvent event);

    List<Cinema> getCityCinemas();

    Page<Cinema> getCinemasPage(int page, int pageSize);

    Page<Cinema> getCinemasPage(int page, int pageSize, Sort sort);

    boolean isCinemaExist(long id);

    Cinema getCinema(long id);

    void deleteCity(String city);

    void createCity(String name);

    void deleteCinema(Cinema cinema);

    void createCinema(City city, String name, String street);

    Cinema getCinema(Long cinemaId);

    FilmScreeningEvent getFilmScreeningEvent(Long filmScreeningEventId);

    void buyTickets(List<Ticket> boughtTickets);

    List<Movie> getCurrentMovies();

    Optional<City> getCityByName(String ipAddress) throws IOException;

    Optional<Cinema> getNearestCinema(InetAddress address) throws IOException;

    Optional<City> getCityByInetAddress(InetAddress address) throws IOException;

    List<FilmScreening> getTodayCinemaFilmScreenings(Cinema cinema);

    Optional<Cinema> getNearestCinema(InetAddress address, City city) throws IOException;

    void deleteCinema(Long id);

    void deleteCity(Long id);

    Map<String, String> getHelpTittleTextMap() throws ParserConfigurationException, ResourceException, SAXException, IOException;

    List<Cinema> getCityCinemas(Long id);
}
