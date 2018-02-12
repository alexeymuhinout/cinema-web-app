package com.rustedbrain.study.course.service;

public interface AuthenticationService {

    boolean isAuthenticated();

    void logOut();

    boolean login(String username, String password, boolean rememberMe);
}
