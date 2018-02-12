package com.rustedbrain.study.course.service;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.rustedbrain.study.course.model.exception.ResourceException;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.*;
import com.rustedbrain.study.course.service.repository.CinemaRepository;
import com.rustedbrain.study.course.service.repository.CityRepository;
import com.rustedbrain.study.course.service.repository.FilmScreeningEventRepository;
import com.rustedbrain.study.course.service.repository.TicketRepository;
import com.rustedbrain.study.course.service.resources.ResourceAccessor;
import com.rustedbrain.study.course.service.util.GoogleMapApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {


    private final Logger logger = Logger.getLogger(CinemaServiceImpl.class.getName());

    @Autowired
    private ResourceAccessor resourceAccessor;

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
    public Set<Cinema> getCityCinemas(City city) {
        return city.getCinemas();
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public Page<City> getCitiesPage(int page, int pageSize) {
        return cityRepository.findAll(PageRequest.of(page, pageSize));
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
    public List<Cinema> getCityCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    public Page<Cinema> getCinemasPage(int page, int pageSize) {
        return getCinemasPage(page, pageSize, Sort.by(Sort.Direction.DESC, "name"));
    }

    @Override
    public Page<Cinema> getCinemasPage(int page, int pageSize, Sort sort) {
        return cinemaRepository.findAll(PageRequest.of(page, pageSize, sort));
    }

    @Override
    public boolean isCinemaExist(long id) {
        return getCinema(id) != null;
    }


    @Override
    public Cinema getCinema(long id) {
        return cinemaRepository.getOne(id);
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
            LookupService lookupService = new LookupService(resourceAccessor.getIPGeoDatabaseFile(),
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
    public Optional<Cinema> getNearestCinema(InetAddress address) throws IOException {
        Location userLocation = getUserLocation(address.getHostAddress());
        String userStreetLocation = GoogleMapApiUtil.getAddressByCoordinates(userLocation.longitude, userLocation.latitude);

        Optional<City> optionalCity = getCityByName(userLocation.city);
        if (optionalCity.isPresent()) {
            return calculateNearestCinemaToAddress(optionalCity.get().getCinemas(), userStreetLocation);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<City> getCityByInetAddress(InetAddress address) throws IOException {
        return getCityByName(getUserLocation(address.getHostAddress()).city);
    }

    @Override
    public List<FilmScreening> getTodayCinemaFilmScreenings(Cinema cinema) {
        List<FilmScreening> filmScreenings = new ArrayList<>();
        for (FilmScreening filmScreening : cinema.getFilmScreenings()) {
            if (filmScreening.isAvailableToBuy() && filmScreening.getStartDate().before(Date.from(Instant.now())) && filmScreening.getEndDate().after(Date.from(Instant.now()))) {
                FilmScreening todayFilmScreening = new FilmScreening();
                todayFilmScreening.setMovie(filmScreening.getMovie());
                todayFilmScreening.setFilmScreeningEvents(filmScreening.getFilmScreeningEvents()
                        .stream().filter(filmScreeningEvent -> filmScreeningEvent.getTime().toLocalTime().isAfter(LocalTime.now())).collect(Collectors.toSet()));
                filmScreenings.add(todayFilmScreening);
            }
        }
        return filmScreenings;
    }

    @Override
    public Optional<Cinema> getNearestCinema(InetAddress address, City city) throws IOException {
        Location userLocation = getUserLocation(address.getHostAddress());
        String userStreetLocation = GoogleMapApiUtil.getAddressByCoordinates(userLocation.longitude, userLocation.latitude);
        return calculateNearestCinemaToAddress(city.getCinemas(), userStreetLocation);
    }

    @Override
    public void deleteCinema(Long id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public Map<String, String> getHelpTittleTextMap() throws ParserConfigurationException, ResourceException, SAXException, IOException {
        return resourceAccessor.getHelpTittleTextMap();
    }

    @Override
    public List<Cinema> getCityCinemas(Long id) {
        return cinemaRepository.getCinemasByCityId(id);
    }

    private Optional<Cinema> calculateNearestCinemaToAddress(Set<Cinema> cinemas, String userStreetLocation) throws IOException {
        TreeMap<Double, Cinema> cinemaDistanceMap = new TreeMap<>();
        for (Cinema cinema : cinemas) {
            cinemaDistanceMap.put(GoogleMapApiUtil.getDistanceBetweenAddresses(userStreetLocation, cinema.getLocation()), cinema);
        }
        Optional<Map.Entry<Double, Cinema>> optionalDistanceCinema = Optional.ofNullable(cinemaDistanceMap.lastEntry());
        return optionalDistanceCinema.map(Map.Entry::getValue);
    }
}
