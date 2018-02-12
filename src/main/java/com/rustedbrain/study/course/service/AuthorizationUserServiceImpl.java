package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.service.repository.AdministratorRepository;
import com.rustedbrain.study.course.service.repository.MemberRepository;
import com.rustedbrain.study.course.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthorizationUserServiceImpl implements AuthorizationUserService {

    private MemberRepository memberRepository;

    private AdministratorRepository administratorRepository;

    private SecureRandom random = new SecureRandom();

    private Map<String, String> rememberedUsers = new HashMap<>();

    public boolean isAuthenticUser(String login, String password) {
        for (Validator validator : Validator.values()) {
            if (validator.isValid(login)) {
                switch (validator) {
                    case MAIL_VALIDATOR: {
                        Optional<User> userOptional = getUserByMail(login);
                        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
                    }
                    case LOGIN_VALIDATOR: {
                        Optional<User> userOptional = getUserByLogin(login);
                        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
                    }
                }
            }
        }
        return false;
    }

    private Optional<User> getUserByLogin(String login) {
        return Optional.empty();
    }

    private Optional<User> getUserByMail(String login) {
        return Optional.empty();
    }

    public String rememberUser(String username) {
        String randomId = new BigInteger(130, random).toString(32);
        rememberedUsers.put(randomId, username);
        return randomId;
    }

    public String getRememberedUser(String id) {
        return rememberedUsers.get(id);
    }

    public void removeRememberedUser(String id) {
        rememberedUsers.remove(id);
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setAdministratorRepository(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }
}
