package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.dto.AuthUser;
import com.rustedbrain.study.course.model.dto.UserInfo;
import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.Administrator;
import com.rustedbrain.study.course.model.persistence.authorization.User;

import java.util.Optional;

public interface AuthorizationUserService {

	Optional<AuthUser> getIdentifiedAuthUser(String login, String password);

	Optional<AuthUser> getAuthUserById(long id);

	String rememberUser(String login, UserRole role);

	AuthUser getRememberedUser(String id);

	void removeRememberedUser(String id);

	boolean isValidCinemaManager(String userLogin, long cinemaId);

	Optional<Administrator> getAdministrator(String userLogin);

	UserPropertiesAccessor getUserPropertiesAccessor(UserRole role);

	User getUser(String login);

	Optional<UserInfo> getUserInfo(long userId);
}
