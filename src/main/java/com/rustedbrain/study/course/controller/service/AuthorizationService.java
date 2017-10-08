package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.model.authorization.User;

public interface AuthorizationService {

    void registerUser(User user) throws IllegalArgumentException;

    void deleteUser(User user) throws IllegalArgumentException;

    boolean isRegisteredUser(String name, String password) throws IllegalArgumentException;

}
