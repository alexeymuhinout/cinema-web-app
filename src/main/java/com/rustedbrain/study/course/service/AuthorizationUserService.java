package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.dto.AuthUser;
import com.rustedbrain.study.course.model.dto.UserRole;

import java.util.Optional;

public interface AuthorizationUserService {

    Optional<AuthUser> getAuthenticUser(String login, String password);

    String rememberUser(String login, UserRole role);

    AuthUser getRememberedUser(String id);

    void removeRememberedUser(String id);
}
