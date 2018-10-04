package com.rustedbrain.study.course.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rustedbrain.study.course.model.dto.AuthUser;
import com.rustedbrain.study.course.model.dto.UserInfo;
import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final String SESSION_USERNAME = "username";
	private static final String SESSION_USER_ROLE = "user_role";
	private static final String COOKIE_NAME = "remember-me";

	private AuthorizationUserService authorizationUserService;

	private static Optional<Cookie> getRememberMeCookie() {
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		return Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst();
	}

	public boolean isCinemaManagementAvailable(long cinemaId) {
		switch (getUserRole()) {
		case ADMINISTRATOR:
			return true;
		case MEMBER:
			return false;
		case MANAGER:
			return authorizationUserService.isValidCinemaManager(getUserLogin(), cinemaId);
		case PAYMASTER:
			return false;
		case MODERATOR:
			return false;
		case NOT_AUTHORIZED:
			return false;
		default:
			throw new IllegalStateException("Something is going wrong. Role behaviour not implemented yet.");
		}
	}

	public UserRole getUserRole() {
		if ( isAuthenticated() ) {
			return UserRole.valueOf(VaadinSession.getCurrent().getAttribute(SESSION_USER_ROLE).toString());
		} else {
			return UserRole.NOT_AUTHORIZED;
		}
	}

	public String getUserLogin() {
		return VaadinSession.getCurrent().getAttribute(SESSION_USERNAME).toString();
	}

	@Override
	public User getAuthenticUser() {
		if ( !isAuthenticated() ) {
			throw new IllegalStateException("Cannot retrieve not authenticated user.");
		}
		Optional<User> optionalUser = Optional.ofNullable(authorizationUserService.getUser(getUserLogin()));
		if ( !optionalUser.isPresent() ) {
			throw new IllegalStateException("User with specified login/mail not found.");
		} else {
			return optionalUser.get();
		}
	}

	@Override
	public List<User> getUsersByRoles(List<UserRole> roles) {
		List<User> users = new ArrayList<>();
		for (UserRole role : roles) {
			users.addAll(authorizationUserService.getUserPropertiesAccessor(role).findAll());
		}
		return users;
	}

	@Override
	public void changeUserName(long id, String name, UserRole authorizedUserRole) {
		switch (authorizedUserRole) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserName(id, name);
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			break;
		default: {
			// TODO: 4/8/18 Add logic to check last renaming request and prevent to change
			// data too often
			// TODO: 4/8/18 Add logic to create request in database about renaming for admin
		}
			break;
		}
	}

	@Override
	public void changeUserLogin(long id, String login, UserRole role) {
		switch (role) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserLogin(id, login);
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			break;
		default: {
			// TODO: 4/8/18 Add logic to check last renaming request and prevent to change
			// data too often
			// TODO: 4/8/18 Add logic to create request in database about renaming for admin
		}
			break;
		}
	}

	@Override
	public void changeUserMail(long id, String mail, UserRole role) {
		switch (role) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserMail(id, mail);
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			break;
		default: {
			// TODO: 4/8/18 Add logic to check last renaming request and prevent to change
			// data too often
			// TODO: 4/8/18 Add logic to create request in database about renaming for admin
		}
			break;
		}
	}

	@Override
	public void changeUserSurname(long id, String surname, UserRole role) {
		switch (role) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserSurname(id, surname);
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			break;
		default: {
			// TODO: 4/8/18 Add logic to check last renaming request and prevent to change
			// data too often
			// TODO: 4/8/18 Add logic to create request in database about renaming for admin
		}
			break;
		}
	}

	@Override
	public void changeUserCity(long id, long cityId, UserRole role) {
		switch (role) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserCity(id, cityId);
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			break;
		default: {
			// TODO: 4/8/18 Add logic to check last renaming request and prevent to change
			// data too often
			// TODO: 4/8/18 Add logic to create request in database about renaming for admin
		}
			break;
		}
	}

	@Override
	public void changeUserBirthday(long id, LocalDate birthday, UserRole role) {
		switch (role) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserBirthday(id, Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			break;
		default: {
			// TODO: 4/8/18 Add logic to check last renaming request and prevent to change
			// data too often
			// TODO: 4/8/18 Add logic to create request in database about renaming for admin
		}
			break;
		}
	}

	@Override
	public void changeUserBlockUntilDate(long id, LocalDateTime blockedUntilDate, String blockDescription,
			UserRole userRole) {
		switch (userRole) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserBlockUntilDateAndDescription(id,
								Date.from(blockedUntilDate.atZone(ZoneId.systemDefault()).toInstant()),
								blockDescription);
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			// TODO: 4/8/18 Create blocking logic for moderator
			break;
		default: {
			throw new IllegalArgumentException("Only administrator and moderator can block another users.");
		}
		}
	}

	@Override
	public void unblockUser(long id, UserRole userRole) {
		switch (userRole) {
		case ADMINISTRATOR: {
			Optional<AuthUser> optionalAuthUser = authorizationUserService.getAuthUserById(id);
			if ( optionalAuthUser.isPresent() ) {
				authorizationUserService.getUserPropertiesAccessor(optionalAuthUser.get().getUserRole())
						.changeUserBlockUntilDateAndDescription(id, Date.from(Instant.now()), "");
			} else {
				throw new IllegalArgumentException("User with specified id not found.");
			}
		}
			// TODO: 4/8/18 Create blocking logic for moderator
			break;
		default: {
			throw new IllegalArgumentException("Only administrator and moderator can unblock another users.");
		}
		}
	}

	@Override
	public Optional<AuthUser> getAuthUserById(long id) {
		return authorizationUserService.getAuthUserById(id);
	}

	@Override
	public Optional<UserInfo> getUserInfo(long userId) {
		return authorizationUserService.getUserInfo(userId);
	}

	@Override
	public boolean isAuthenticated() {
		return (VaadinSession.getCurrent().getAttribute(SESSION_USERNAME) != null
				&& VaadinSession.getCurrent().getAttribute(SESSION_USER_ROLE) != null) || loginRememberedUser();
	}

	public void logOut() {
		Optional<Cookie> cookie = getRememberMeCookie();
		if ( cookie.isPresent() ) {
			String id = cookie.get().getValue();
			authorizationUserService.removeRememberedUser(id);
			deleteRememberMeCookie();
		}

		VaadinSession.getCurrent().close();
		Page.getCurrent().setLocation("");
	}

	private boolean loginRememberedUser() {
		Optional<Cookie> rememberMeCookie = getRememberMeCookie();

		if ( rememberMeCookie.isPresent() ) {
			String id = rememberMeCookie.get().getValue();
			AuthUser authUser = authorizationUserService.getRememberedUser(id);

			if ( authUser != null ) {
				VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, authUser.getLogin());
				VaadinSession.getCurrent().setAttribute(SESSION_USER_ROLE, authUser.getUserRole().name());
				return true;
			}
		}
		return false;
	}

	private void rememberUser(String username, UserRole role) {
		String id = authorizationUserService.rememberUser(username, role);

		Cookie cookie = new Cookie(COOKIE_NAME, id);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 30); // valid for 30 days
		VaadinService.getCurrentResponse().addCookie(cookie);
	}

	private void deleteRememberMeCookie() {
		Cookie cookie = new Cookie(COOKIE_NAME, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		VaadinService.getCurrentResponse().addCookie(cookie);
	}

	@Override
	public boolean login(String loginOrMail, String password, boolean rememberMe) {
		Optional<AuthUser> optionalUser = authorizationUserService.getIdentifiedAuthUser(loginOrMail, password);
		if ( optionalUser.isPresent() ) {
			AuthUser user = optionalUser.get();
			VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, user.getLogin());
			VaadinSession.getCurrent().setAttribute(SESSION_USER_ROLE, user.getUserRole().name());
			if ( rememberMe ) {
				rememberUser(user.getLogin(), user.getUserRole());
			}
			return true;
		}
		return false;
	}

	@Autowired
	public void setAuthorizationUserService(AuthorizationUserService authorizationUserService) {
		this.authorizationUserService = authorizationUserService;
	}
}
