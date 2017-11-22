package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.controller.repository.CinemaRepository;
import com.rustedbrain.study.course.controller.repository.CityRepository;
import com.rustedbrain.study.course.model.authorization.User;
import com.rustedbrain.study.course.model.cinema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public List<Movie> getMovies(Cinema cinema, Date date) {
        return null;
    }

    @Override
    public List<Cinema> getCinemas(City city) {
        return null;
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public List<Comment> getMessages(Movie movie) {
        return null;
    }

    @Override
    public void addFilmScreening(FilmScreening filmScreening) {

    }

    @Override
    public List<Seat> getAvailableSeats(Cinema cinema, FilmScreeningEvent event) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void lockTicket(User user, FilmScreeningEvent event) {

    }

    @Override
    public void unlockTicket(User user, FilmScreeningEvent event) {

    }

    @Override
    public List<Cinema> getCinemas() {
        return cinemaRepository.findAll();
    }
}
