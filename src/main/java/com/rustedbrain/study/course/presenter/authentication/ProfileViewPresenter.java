package com.rustedbrain.study.course.presenter.authentication;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.rustedbrain.study.course.model.dto.AuthUser;
import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.presenter.authentication.util.CinemaEditPresenter;
import com.rustedbrain.study.course.presenter.authentication.util.CinemaHallEditPresenter;
import com.rustedbrain.study.course.presenter.authentication.util.CityEditPresenter;
import com.rustedbrain.study.course.presenter.authentication.util.MovieEditPresenter;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.authentication.ProfileView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ProfileViewPresenter implements Serializable, ProfileView.ViewListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5933356439072774517L;
	private static final Logger logger = Logger.getLogger(ProfileViewPresenter.class.getName());
	private final CinemaService cinemaService;
	private final AuthenticationService authenticationService;
	private final CityEditPresenter cityEditPresenter;

	private ProfileView view;
	private CinemaEditPresenter cinemaEditPresenter;
	private CinemaHallEditPresenter cinemaHallEditPresenter;
	private MovieEditPresenter movieEditPresenter;

	@Autowired
	public ProfileViewPresenter(CinemaService cinemaService, AuthenticationService authenticationService) {
		this.cinemaService = cinemaService;
		this.authenticationService = authenticationService;
		cityEditPresenter = new CityEditPresenter(cinemaService);
		cinemaEditPresenter = new CinemaEditPresenter(cinemaService);
		cinemaHallEditPresenter = new CinemaHallEditPresenter(cinemaService);
		movieEditPresenter = new MovieEditPresenter(cinemaService);
	}

	@Override
	public void entered(ViewChangeListener.ViewChangeEvent event) {
		UserRole role = authenticationService.getUserRole();
		switch (role) {
		case ADMINISTRATOR: {
			addMessagesTab(role);
			addProfileInfoTab(role);
			addProfileEditTab(role);
			addAdministrationTab(role);
			addStatisticsTab(role);
		}
			break;
		case PAYMASTER:
			addMessagesTab(role);
			addProfileInfoTab(role);
			addProfileEditTab(role);
			addAdministrationTab(role);
			break;
		case MANAGER:
			addMessagesTab(role);
			addProfileInfoTab(role);
			addProfileEditTab(role);
			addAdministrationTab(role);
			break;
		case MEMBER:
			addMessagesTab(role);
			addProfileInfoTab(role);
			addProfileEditTab(role);
			break;
		case MODERATOR:
			addMessagesTab(role);
			addProfileInfoTab(role);
			addProfileEditTab(role);
			addAdministrationTab(role);
			break;
		case NOT_AUTHORIZED:
			view.showError("User not authorized.");
			break;
		}
	}

	private void addStatisticsTab(UserRole role) {
		logger.info("Statistics tab successfully added.");
		view.addStatisticsTab();
	}

	private void addAdministrationTab(UserRole role) {
		logger.info("Administration tab successfully added.");
		User currUser = authenticationService.getAuthenticUser();
		List<City> cities = cinemaService.getCities();
		List<Movie> movies = cinemaService.getMovies();

		view.addAdministrationTab(currUser, cities, movies);
	}

	private void addProfileEditTab(UserRole role) {
		User currUser = authenticationService.getAuthenticUser();
		List<City> cities = cinemaService.getCities();

		switch (role) {
		case ADMINISTRATOR: {
			List<User> users = authenticationService.getUsersByRoles(Arrays.stream(UserRole.values())
					.filter(rolePredicate -> !rolePredicate.equals(UserRole.NOT_AUTHORIZED))
					.collect(Collectors.toList()));
			view.addProfileAdminEditTab(currUser, users, cities);
		}
			break;
		case MODERATOR: {
			List<User> users = authenticationService.getUsersByRoles(Collections.singletonList(UserRole.MEMBER));
			view.addProfileAdminEditTab(currUser, users, cities);
		}
			break;
		default: {
			view.addProfileEditTab(currUser, cities);
		}
		}
		logger.info("Profile edit tab successfully added.");
	}

	private void addProfileInfoTab(UserRole role) {
		switch (role) {
		case ADMINISTRATOR: {
			List<User> users = authenticationService.getUsersByRoles(Arrays.stream(UserRole.values())
					.filter(rolePredicate -> !rolePredicate.equals(UserRole.NOT_AUTHORIZED))
					.collect(Collectors.toList()));
			view.addAdminProfileInfoTab(authenticationService.getAuthenticUser(), users);
			logger.info("Profile info tab successfully added.");
		}
			break;
		default: {
			view.addProfileInfoTab(authenticationService.getAuthenticUser());
			logger.info("Profile info tab successfully added.");
		}
			break;
		}
	}

	private void addMessagesTab(UserRole role) {
		logger.info("Messages tab successfully added.");
	}

	@Override
	public void setView(ProfileView view) {
		this.view = view;
	}

	@Override
	public void buttonChangeNameClicked(long id, String name) {
		authenticationService.changeUserName(id, name, authenticationService.getUserRole());
		view.reload();
	}

	@Override
	public void buttonChangeLoginClicked(long id, String login) {
		authenticationService.changeUserLogin(id, login, authenticationService.getUserRole());
	}

	@Override
	public void buttonChangeMailClicked(long id, String mail) {
		authenticationService.changeUserMail(id, mail, authenticationService.getUserRole());
		view.reload();
	}

	@Override
	public void buttonChangeSurnameClicked(long id, String surname) {
		authenticationService.changeUserSurname(id, surname, authenticationService.getUserRole());
		view.reload();
	}

	@Override
	public void buttonChangeCityClicked(long id, long cityId) {
		authenticationService.changeUserCity(id, cityId, authenticationService.getUserRole());
		view.reload();
	}

	@Override
	public void buttonChangeBirthdayClicked(long id, LocalDate birthday) {
		authenticationService.changeUserBirthday(id, birthday, authenticationService.getUserRole());
		view.reload();
	}

	@Override
	public void comboBoxEditUserSelected(User selectedUser) {
		view.setAdminEditUserSelected(selectedUser);
	}

	@Override
	public void comboBoxInfoUserSelected(User selectedUser) {
		view.setAdminInfoUserSelected(selectedUser);
	}

	@Override
	public void buttonUnblockClicked(long id) {
		authenticationService.unblockUser(id, authenticationService.getUserRole());
		view.reload();
	}

	@Override
	public void buttonBlockClicked(long id) {
		Optional<AuthUser> user = authenticationService.getAuthUserById(id);
		user.ifPresent(authUser -> {
			logger.info("User successfully retrieved: " + authUser);
			view.showUserBlockWindow(id, authUser.getLogin(), authUser.getUserRole());
		});
	}

	@Override
	public void buttonEditShowMeClicked(List<User> users) {
		String myLogin = authenticationService.getUserLogin();
		Optional<User> userOptional = users.stream().filter(user -> user.getLogin().equals(myLogin)).findAny();
		userOptional.ifPresent(user -> view.setAdminEditUserSelected(user));
	}

	@Override
	public void buttonInfoShowMeClicked(List<User> users) {
		String myLogin = authenticationService.getUserLogin();
		Optional<User> userOptional = users.stream().filter(user -> user.getLogin().equals(myLogin)).findAny();
		userOptional.ifPresent(user -> view.setAdminInfoUserSelected(user));
	}

	@Override
	public void buttonBlockSubmitClicked(long id, LocalDateTime blockDateTime, String blockDescription) {
		authenticationService.changeUserBlockUntilDate(id, blockDateTime, blockDescription,
				authenticationService.getUserRole());
		view.closeUserBlockWindow();
		view.showWarning("User successfully blocked until " + blockDateTime);
	}

	@Override
	public CityEditPresenter getCityEditPresenter() {
		return cityEditPresenter;
	}

	@Override
	public CinemaEditPresenter getCinemaEditPresenter() {
		return cinemaEditPresenter;
	}

	@Override
	public CinemaHallEditPresenter getCinemaHallEditPresenter() {
		return cinemaHallEditPresenter;
	}

	@Override
	public void reload() {
		view.reload();

	}

	@Override
	public MovieEditPresenter getMovieEditPresenter() {
		return movieEditPresenter;
	}
}
