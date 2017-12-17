package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.model.authorization.Member;
import com.rustedbrain.study.course.model.authorization.User;

public interface AuthorizationService {

    void registerMember(Member member) throws IllegalArgumentException;

    void deleteMember(Member member) throws IllegalArgumentException;

    boolean isRegisteredUser(String name, String password) throws IllegalArgumentException;

    boolean isUniqueUser(String mail, String login);

    boolean isRegisteredAdministrator(String name, String password) throws IllegalArgumentException;

    boolean isRegisteredMember(String name, String password) throws IllegalArgumentException;

    User getUser(String login);

    void deleteUser(User user);
}
