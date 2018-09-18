package com.rustedbrain.study.course.service;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.rustedbrain.study.course.model.dto.TicketInfo;
import com.rustedbrain.study.course.model.exception.ResourceException;
import com.rustedbrain.study.course.model.persistence.authorization.Member;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.Comment;
import com.rustedbrain.study.course.model.persistence.cinema.CommentReputation;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreeningEvent;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.model.persistence.cinema.Row;
import com.rustedbrain.study.course.model.persistence.cinema.Seat;
import com.rustedbrain.study.course.model.persistence.cinema.Ticket;
import com.rustedbrain.study.course.service.repository.CinemaHallRepository;
import com.rustedbrain.study.course.service.repository.CinemaRepository;
import com.rustedbrain.study.course.service.repository.CityRepository;
import com.rustedbrain.study.course.service.repository.CommentRepository;
import com.rustedbrain.study.course.service.repository.CommentReputationRepository;
import com.rustedbrain.study.course.service.repository.FilmScreeningEventRepository;
import com.rustedbrain.study.course.service.repository.FilmScreeningRepository;
import com.rustedbrain.study.course.service.repository.MemberRepository;
import com.rustedbrain.study.course.service.repository.MovieRepository;
import com.rustedbrain.study.course.service.repository.RowRepository;
import com.rustedbrain.study.course.service.repository.SeatRepository;
import com.rustedbrain.study.course.service.repository.TicketRepository;
import com.rustedbrain.study.course.service.resources.ResourceAccessor;
import com.rustedbrain.study.course.service.util.GoogleMapApiUtil;
import com.rustedbrain.study.course.service.util.Validator;

@Service
public class CinemaServiceImpl implements CinemaService {

	private final Logger logger = Logger.getLogger(CinemaServiceImpl.class.getName());

	@Autowired
	private ResourceAccessor resourceAccessor;

	private CityRepository cityRepository;

	private CinemaRepository cinemaRepository;
	private CinemaHallRepository cinemaHallRepository;

	private FilmScreeningEventRepository filmScreeningEventRepository;

	private FilmScreeningRepository filmScreeningRepository;

	private TicketRepository ticketRepository;

	private SeatRepository seatRepository;
	
	private RowRepository rowRepository;

	private MemberRepository memberRepository;

	private MovieRepository movieRepository;

	private CommentRepository commentRepository;

	private CommentReputationRepository commentReputationRepository;

	@Autowired
	public void setCommentReputationRepository(CommentReputationRepository commentReputationRepository) {
		this.commentReputationRepository = commentReputationRepository;
	}

	@Autowired
	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Autowired
	public void setResourceAccessor(ResourceAccessor resourceAccessor) {
		this.resourceAccessor = resourceAccessor;
	}

	@Autowired
	public void setFilmScreeningRepository(FilmScreeningRepository filmScreeningRepository) {
		this.filmScreeningRepository = filmScreeningRepository;
	}

	@Autowired
	public void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Autowired
	public void setSeatRepository(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}
	
	@Autowired
	public void setRowRepository(RowRepository rowRepository) {
		this.rowRepository = rowRepository;
	}

	@Autowired
	public void setTicketRepository(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Autowired
	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Autowired
	public void setCinemaHallRepository(CinemaHallRepository cinemaHallRepository) {
		this.cinemaHallRepository = cinemaHallRepository;
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
	public List<City> getCities() {
		return cityRepository.findAll();
	}

	@Override
	public Page<City> getCitiesPage(int page, int pageSize) {
		return cityRepository.findAll(new PageRequest(page, pageSize));
	}

	@Override
	public Page<Cinema> getCinemasPage(int page, int pageSize) {
		return getCinemasPage(page, pageSize, new Sort(Sort.Direction.DESC, "name"));
	}

	@Override
	public Page<Cinema> getCinemasPage(int page, int pageSize, Sort sort) {
		return cinemaRepository.findAll(new PageRequest(page, pageSize, sort));
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
	public Cinema getCinema(Long cinemaId) {
		return cinemaRepository.getOne(cinemaId);
	}

	@Override
	public FilmScreeningEvent getFilmScreeningEvent(Long filmScreeningEventId) {
		return filmScreeningEventRepository.getOne(filmScreeningEventId);
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
		String userStreetLocation = GoogleMapApiUtil.getAddressByCoordinates(userLocation.longitude,
				userLocation.latitude);

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
			if (filmScreening.isAvailableToBuy() && filmScreening.getStartDate().before(Date.from(Instant.now()))
					&& filmScreening.getEndDate().after(Date.from(Instant.now()))) {
				FilmScreening todayFilmScreening = new FilmScreening();
				todayFilmScreening.setMovie(filmScreening.getMovie());
				todayFilmScreening.setFilmScreeningEvents(filmScreening.getFilmScreeningEvents().stream().filter(
						filmScreeningEvent -> filmScreeningEvent.getTime().toLocalTime().isAfter(LocalTime.now()))
						.collect(Collectors.toSet()));
				filmScreenings.add(todayFilmScreening);
			}
		}
		return filmScreenings;
	}

	@Override
	public Optional<Cinema> getNearestCinema(InetAddress address, City city) throws IOException {
		Location userLocation = getUserLocation(address.getHostAddress());
		String userStreetLocation = GoogleMapApiUtil.getAddressByCoordinates(userLocation.longitude,
				userLocation.latitude);
		return calculateNearestCinemaToAddress(city.getCinemas(), userStreetLocation);
	}

	@Override
	public void deleteCinema(Long id) {
		cinemaRepository.delete(id);
	}

	@Override
	public void deleteCity(Long id) {
		cityRepository.delete(id);
	}

	@Override
	public Map<String, String> getHelpTittleTextMap()
			throws ParserConfigurationException, ResourceException, SAXException, IOException {
		return resourceAccessor.getHelpTittleTextMap();
	}

	@Override
	public Map<Integer, List<Integer>> getCinemaHallSeatCoordinateMap(CinemaHall cinemaHall)
			throws ParserConfigurationException, ResourceException, SAXException, IOException {
		return resourceAccessor.getCinemaHallSeatCoordinateMap(cinemaHall);
	}

	@Override
	public List<Cinema> getCityCinemas(Long id) {
		return cinemaRepository.getCinemasByCityId(id);
	}

	@Override
	public List<Seat> getSeats(List<Long> seatIds) {
		return seatRepository.findAll(seatIds);
	}

	@Override
	public List<TicketInfo> reserveTickets(String name, String surname, String login,
			FilmScreeningEvent filmScreeningEvent, List<Seat> seats) {
		Optional<Member> optionalMember = Optional.ofNullable(memberRepository.findByLogin(login));
		if (optionalMember.isPresent()) {
			List<TicketInfo> tickets = new ArrayList<>();
			for (Seat seat : seats) {
				Ticket ticket = new Ticket();
				Date date = new Date();
				ticket.setRegistrationDate(date);
				ticket.setLastAccessDate(date);
				ticket.setSeat(seat);
				ticket.setMember(optionalMember.get());
				ticket.setClientName(name);
				ticket.setClientSurname(surname);
				ticket.setEvent(filmScreeningEvent);
				ticket.setReserved(true);
				ticketRepository.save(ticket);
				tickets.add(new TicketInfo(ticket));
			}
			return tickets;
		} else {
			throw new IllegalArgumentException("Only members can buy tickets");
		}
	}

	@Override
	public List<TicketInfo> reserveTickets(String name, String surname, FilmScreeningEvent filmScreeningEvent,
			List<Seat> seats) {
		List<TicketInfo> tickets = new ArrayList<>();
		for (Seat seat : seats) {
			Ticket ticket = new Ticket();
			Date date = new Date();
			ticket.setRegistrationDate(date);
			ticket.setLastAccessDate(date);
			ticket.setSeat(seat);
			ticket.setClientName(name);
			ticket.setClientSurname(surname);
			ticket.setEvent(filmScreeningEvent);
			ticket.setReserved(true);
			ticketRepository.save(ticket);
			tickets.add(new TicketInfo(ticket));
		}
		return tickets;
	}

	@Override
	public Member getMemberByLogin(String userLogin) {
		return memberRepository.findByLogin(userLogin);
	}

	@Override
	public Set<FilmScreening> getDayFilmScreenings(long cinemaId, LocalDate day) {
		Set<FilmScreening> filmScreenings = new HashSet<>(
				filmScreeningRepository.getFilmScreening(cinemaId, java.sql.Date.valueOf(day)));

		for (FilmScreening filmScreening : filmScreenings) {
			filmScreening.getFilmScreeningEvents()
					.removeIf(filmScreeningEvent -> !filmScreeningEvent.getDate().equals(java.sql.Date.valueOf(day)));
		}

		return filmScreenings;
	}

	@Override
	public List<TicketInfo> getTicketsInfo(List<Long> ticketIds) {
		return ticketRepository.findAll(ticketIds).stream().map(TicketInfo::new).collect(Collectors.toList());
	}

	@Override
	public void renameCinema(long id, String value) {
		cinemaRepository.renameCinema(id, value);
	}

	@Override
	public void registerMember(String login, String password, String name, String surname, long cityId,
			LocalDate birthday, String mail) {
		if (!Validator.LOGIN_VALIDATOR.isValid(login)) {
			throw new IllegalArgumentException(
					"Login is not valid. You can use only characters and digits in your login.");
		} else if (!Validator.MAIL_VALIDATOR.isValid(mail)) {
			throw new IllegalArgumentException("Mail is not valid. Please check inputted mail.");
		}
		Member member = new Member(login, password, mail);
		member.setName(name);
		member.setSurname(surname);
		member.setCity(cityRepository.getOne(cityId));
		member.setLastAccessDate(new Date());
		member.setRegistrationDate(new Date());
		member.setBirthday(Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		memberRepository.save(member);
	}

	@Override
	public Optional<Movie> getMovie(long id) {
		return Optional.ofNullable(movieRepository.findOne(id));
	}

	@Override
	public void createMessage(Movie movie, User authenticUser, String message) {
		Comment comment = new Comment(authenticUser, movie, message);
		comment.setLastAccessDate(new Date());
		comment.setRegistrationDate(new Date());
		commentRepository.save(comment);
	}

	@Override
	public void deleteComment(long id) {
		commentRepository.delete(id);
	}

	@Override
	public void addMinusCommentReputation(long commentId, User user) {
		CommentReputation commentReputation = createMinusCommentReputation(commentId, user);
		commentReputationRepository.save(commentReputation);
	}

	@Override
	public void invertCommentReputation(CommentReputation commentReputation) {
		commentReputationRepository.setPlus(commentReputation.getId(), !commentReputation.isPlus());
	}

	@Override
	public void addPlusCommentReputation(long commentId, User user) {
		CommentReputation commentReputation = createMinusCommentReputation(commentId, user);
		commentReputation.setPlus(true);
		commentReputationRepository.save(commentReputation);
	}

	@Override
	public void renameCity(City selectedCity, String newCityName) {
		cityRepository.renameCity(selectedCity.getId(), newCityName);
	}

	@Override
	public void createCity(String name) {
		City city = new City(name);
		cityRepository.save(city);
	}

	@Override
	public void editCinema(Cinema selectedCinema, String newCinemaName, City newCity, String newCinemaLocation) {
		cinemaRepository.editCinema(selectedCinema.getId(), newCinemaName, newCity, newCinemaLocation);
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
	public void editCinemaHall(CinemaHall selectedCinemaHall, String newCinemaHallName, Cinema newCinema) {
		cinemaHallRepository.editCinemaHall(selectedCinemaHall.getId(), newCinemaHallName, newCinema);
	}

	@Override
	public void deleteCinemaHall(long id) {
		cinemaHallRepository.delete(id);
	}

	private CommentReputation createMinusCommentReputation(long commentId, User user) {
		CommentReputation commentReputation = new CommentReputation();
		commentReputation.setLastAccessDate(new Date());
		commentReputation.setRegistrationDate(new Date());
		commentReputation.setUser(user);
		commentReputation.setComment(commentRepository.getOne(commentId));
		commentReputationRepository.save(commentReputation);
		return commentReputation;
	}

	private Optional<Cinema> calculateNearestCinemaToAddress(Set<Cinema> cinemas, String userStreetLocation)
			throws IOException {
		TreeMap<Double, Cinema> cinemaDistanceMap = new TreeMap<>();
		for (Cinema cinema : cinemas) {
			cinemaDistanceMap.put(
					GoogleMapApiUtil.getDistanceBetweenAddresses(userStreetLocation, cinema.getLocation()), cinema);
		}
		Optional<Map.Entry<Double, Cinema>> optionalDistanceCinema = Optional.ofNullable(cinemaDistanceMap.lastEntry());
		return optionalDistanceCinema.map(Map.Entry::getValue);
	}

	@Override
	public long createCinemaHall(String cinemaHallName, Cinema cinema) {
		CinemaHall cinemaHall = new CinemaHall(cinemaHallName);
		cinemaHall.setCinema(cinema);
		cinemaHallRepository.save(cinemaHall);
		return cinemaHall.getId();
	}

	@Override
	public void setCinemaHallSeatMap(long cinameHallId, Map<Integer, List<Integer>> cinemaHallSeatCoordinateMultiMap)
			throws ParserConfigurationException, ResourceException, SAXException, IOException {
		resourceAccessor.setCinemaHallSeatMap(cinameHallId, cinemaHallSeatCoordinateMultiMap);
	}

	@Override
	public Optional<CinemaHall> getCinemaHall(long id) {
		return Optional.ofNullable(cinemaHallRepository.findOne(id));
	}

	@Override
	public void editCinemaHallSeats(long cinemaHallId, Map<Integer, List<Integer>> cinemaHallSeatCoordinateMap) {
		CinemaHall cinemaHall = cinemaHallRepository.getOne(cinemaHallId);
		
		Set<Row> rows = new HashSet<>();
		cinemaHallSeatCoordinateMap.entrySet().stream().forEach(enty -> {
			Row row = new Row();
			row.setCinemaHall(cinemaHall);
			row.setNumber(enty.getKey() + 1);
			row.setRegistrationDate(new Date());
			row.setLastAccessDate(new Date());

			Set<Seat> seats = new HashSet<>();
			enty.getValue().forEach(value -> {
				Seat seat = new Seat(value + 1, 1, 12.50);
				seat.setRow(row);
				seat.setLastAccessDate(new Date());
				seat.setRegistrationDate(new Date());
				seats.add(seat);
				
			});
			row.setSeats(seats);
			rows.add(row);
		});

		cinemaHallRepository.editCinemaHallSeats(cinemaHallId, rows);
	}
}
