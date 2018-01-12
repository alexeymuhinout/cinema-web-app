package com.rustedbrain.study.course.service;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.rustedbrain.study.course.model.authorization.User;
import com.rustedbrain.study.course.model.cinema.*;
import com.rustedbrain.study.course.service.repository.CinemaRepository;
import com.rustedbrain.study.course.service.repository.CityRepository;
import com.rustedbrain.study.course.service.repository.FilmScreeningEventRepository;
import com.rustedbrain.study.course.service.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CinemaServiceImpl implements CinemaService {

    private static final String IP_GEO_DATABASE_RESOURCE = "GeoLiteCity.dat";

    private final Logger logger = Logger.getLogger(CinemaServiceImpl.class.getName());

    private CityRepository cityRepository;

    private CinemaRepository cinemaRepository;

    private FilmScreeningEventRepository filmScreeningEventRepository;

    private TicketRepository ticketRepository;

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setCinemaRepository(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Autowired
    public void setFilmScreeningEventRepository(FilmScreeningEventRepository filmScreeningEventRepository) {
        this.filmScreeningEventRepository = filmScreeningEventRepository;
    }

    @Override
    public List<Movie> getCurrentMovies(Cinema cinema, Date date) {
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
    public boolean isCinemaExist(String name) {
        return getCinema(name) != null;
    }


    @Override
    public Cinema getCinema(String name) {
        return cinemaRepository.findByName(name);
    }

    @Override
    public void deleteCity(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            throw new IllegalArgumentException("City name cannot be empty");
        } else {
            Optional<City> optionalCity = Optional.ofNullable(cityRepository.findByName(cityName));
            if (optionalCity.isPresent()) {
                cityRepository.delete(optionalCity.get());
            } else {
                throw new IllegalArgumentException("City with specified name not found");
            }
        }
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
    public Cinema getCinema(Long cinemaId) {
        return cinemaRepository.getOne(cinemaId);
    }


    @Override
    public FilmScreeningEvent getFilmScreeningEvent(Long filmScreeningEventId) {
        return filmScreeningEventRepository.getOne(filmScreeningEventId);
    }

    @Override
    public void buyTickets(List<Ticket> boughtTickets) {
        ticketRepository.saveAll(boughtTickets);
    }

    @Override
    public List<Movie> getCurrentMovies() {


        return null;
    }

    private Location getUserLocation(String ipAddress) throws IOException {
        try {
            LookupService lookupService = new LookupService(getResourceFile(IP_GEO_DATABASE_RESOURCE),
                    LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
            Optional<Location> optionalLocation = Optional.ofNullable(lookupService.getLocation(ipAddress));
            if (optionalLocation.isPresent()) {
                return optionalLocation.get();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error occurred while getting location by user's IP address", e);
            throw e;
        }
        throw new NoSuchElementException("Location for entered IP address not found");
    }

    @Override
    public Optional<City> getCityByName(String cityName) {
        return Optional.ofNullable(cityRepository.findByName(cityName));
    }

    @Override
    public Optional<Cinema> getNearestCinema(float latitude, float longitude) {


        return Optional.empty();
    }

    @Override
    public Optional<City> getCityByInetAddress(InetAddress address) throws IOException {
        return getCityByName(getUserLocation(address.getHostAddress()).city);
    }

    private File getResourceFile(String fileName) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
    }
}
