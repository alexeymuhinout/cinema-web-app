package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.dto.UserRole;

public interface AuthenticationService {

    boolean isAuthenticated();

    void logOut();

    boolean login(String username, String password, boolean rememberMe);

    UserRole getUserRole();

    String getUserLogin();
}
