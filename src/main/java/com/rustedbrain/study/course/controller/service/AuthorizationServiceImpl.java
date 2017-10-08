package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.model.authorization.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {


    @Override
    public void registerUser(User user) throws IllegalArgumentException {

    }

    @Override
    public void deleteUser(User user) throws IllegalArgumentException {

    }

    @Override
    public boolean isRegisteredUser(String name, String password) throws IllegalArgumentException {
        return false;
    }
}
