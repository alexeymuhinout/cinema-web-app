package com.rustedbrain.study.course.service;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.xml.sax.SAXException;

import com.rustedbrain.study.course.model.dto.TicketInfo;
import com.rustedbrain.study.course.model.exception.ResourceException;
import com.rustedbrain.study.course.model.persistence.authorization.Member;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.Actor;
import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.CommentReputation;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreeningEvent;
import com.rustedbrain.study.course.model.persistence.cinema.Genre;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.model.persistence.cinema.Seat;

public interface CinemaService {

	List<City> getCities();

	Page<City> getCitiesPage(int page, int pageSize);

	Page<Cinema> getCinemasPage(int page, int pageSize);

	Page<Cinema> getCinemasPage(int page, int pageSize, Sort sort);

	Cinema getCinema(long id);

	void deleteCity(String city);

	void createCinema(City city, String name, String street);

	Cinema getCinema(Long cinemaId);

	FilmScreeningEvent getFilmScreeningEvent(Long filmScreeningEventId);

	Optional<City> getCityByName(String ipAddress) throws IOException;

	Optional<Cinema> getNearestCinema(InetAddress address) throws IOException;

	Optional<City> getCityByInetAddress(InetAddress address) throws IOException;

	List<FilmScreening> getTodayCinemaFilmScreenings(Cinema cinema);

	Optional<Cinema> getNearestCinema(InetAddress address, City city) throws IOException;

	void deleteCinema(Long id);

	void deleteCity(Long id);

	Map<String, String> getHelpTittleTextMap()
			throws ParserConfigurationException, ResourceException, SAXException, IOException;

	List<Cinema> getCityCinemas(Long id);

	List<Seat> getSeats(List<Long> seatIds);

	List<TicketInfo> reserveTickets(String name, String surname, String login, FilmScreeningEvent filmScreeningEvent,
			List<Seat> seats);

	List<TicketInfo> reserveTickets(String name, String surname, FilmScreeningEvent filmScreeningEvent,
			List<Seat> seats);

	List<Actor> getActors();

	List<Genre> getGenres();

	Member getMemberByLogin(String userLogin);

	Set<FilmScreening> getDayFilmScreenings(long cinemaId, LocalDate day);

	List<TicketInfo> getTicketsInfo(List<Long> ticketIds);

	void renameCinema(long id, String value);

	void registerMember(String login, String password, String name, String surname, long id, LocalDate birthday,
			String mail);

	Optional<Movie> getMovie(long id);

	void createMessage(Movie movie, User authenticUser, String textArea);

	void deleteComment(long id);

	void addMinusCommentReputation(long commentId, User userId);

	void invertCommentReputation(CommentReputation commentReputation);

	void addPlusCommentReputation(long id, User user);

	void renameCity(City selectedCity, String newCityName);

	void createCity(String newCityName);

	void editCinema(Cinema selectedCinema, String newCinemaName, City newCity, String newCinemaLocation);

	void editCinemaHall(CinemaHall selectedCinemaHall, String newCinemaHallName, Cinema newCinema);

	void deleteCinemaHall(long id);

	long createCinemaHall(String cinemaHallName, Cinema cinema);

	void setCinemaHallSeatMap(long cinameHallId, Map<Integer, List<Integer>> cinemaHallSeatCoordinateMap)
			throws ParserConfigurationException, ResourceException, SAXException, IOException;

	Map<Integer, List<Integer>> getCinemaHallSeatCoordinateMap(CinemaHall cinemaHall)
			throws ParserConfigurationException, ResourceException, SAXException, IOException;

	Optional<CinemaHall> getCinemaHall(long id);

	void saveCinemaHallSeatsFromXML(long cinemaHallId, Map<Integer, List<Integer>> cinemaHallSeatCoordinateMap);

	void deleteMovie(long movieId);

	void editMovie(Movie selectedMovie, String newOriginalMovieName, String newLocalizeMovieName, String newCountry,
			LocalDateTime newMovieReleaseDate);

	void editMovie(Movie editedMovie);

	long createMovie(String localizedName, String originalName, LocalDateTime releaseDate);

	List<Movie> getMovies();
}
