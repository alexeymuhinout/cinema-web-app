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

    @Override
    public boolean isCityExist(String name) {
        return getCity(name) != null;
    }

    @Override
    public boolean isCinemaExist(String name) {
        return getCinema(name) != null;
    }

    @Override
    public City getCity(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public Cinema getCinema(String name) {
        return cinemaRepository.findByName(name);
    }

    @Override
    public void deleteCity(City city) {
        cityRepository.delete(city);
    }

    @Override
    public void createCity(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("City name cannot be empty");
        } else {
            cityRepository.save(new City(name));
        }
    }

    @Override
    public void deleteCinema(Cinema cinema) {
        cinemaRepository.delete(cinema);
    }

    @Override
    public void createCinema(City city, String name, String street) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cinema name cannot be empty");
        } else if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Cinema street cannot be empty");
        } else {
            cinemaRepository.save(new Cinema(city, name, street));
        }

    }

    @Override
    public void deleteCityByName(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            throw new IllegalArgumentException("Cinema name cannot be empty");
        } else {
            cityRepository.deleteCityByName(cityName);
        }
    }


}
