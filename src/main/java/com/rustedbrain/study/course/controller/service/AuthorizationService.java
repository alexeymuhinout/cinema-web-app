package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.model.authorization.Member;

public interface AuthorizationService {

    void registerMember(Member member) throws IllegalArgumentException;

    void deleteMember(Member member) throws IllegalArgumentException;

    boolean isRegisteredUser(String name, String password) throws IllegalArgumentException;

    boolean isUniqueUser(String mail, String login);
}
