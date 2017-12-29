package com.rustedbrain.study.course.service;

public interface AuthorizationUserService {

    boolean isAuthenticUser(String login, String password);

    String rememberUser(String username);

    String getRememberedUser(String id);

    void removeRememberedUser(String id);
}
