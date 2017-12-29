package com.rustedbrain.study.course.service;

public interface AuthService {

    boolean isAuthenticated();

    void logOut();

    boolean loginRememberedUser();

    void rememberUser(String username);

    boolean login(String username, String password, boolean rememberMe);
}
