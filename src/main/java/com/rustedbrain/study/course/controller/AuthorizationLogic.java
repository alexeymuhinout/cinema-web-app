package com.rustedbrain.study.course.controller;

import com.rustedbrain.study.course.model.authorization.User;

public interface AuthorizationLogic {

    void registerUser(User user) throws IllegalArgumentException;

    void deleteUser(User user) throws IllegalArgumentException;

    boolean isRegisteredUser(String name, String password) throws IllegalArgumentException;

}
