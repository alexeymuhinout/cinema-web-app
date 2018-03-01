package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.dto.AuthUser;
import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.*;
import com.rustedbrain.study.course.service.repository.*;
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

    private AdministratorRepository administratorRepository;
    private ManagerRepository managerRepository;
    private MemberRepository memberRepository;
    private ModeratorRepository moderatorRepository;
    private PaymasterRepository paymasterRepository;

    private SecureRandom random = new SecureRandom();
    private Map<String, AuthUser> rememberedUsers = new HashMap<>();

    @Autowired
    public void setAdministratorRepository(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setModeratorRepository(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    @Autowired
    public void setPaymasterRepository(PaymasterRepository paymasterRepository) {
        this.paymasterRepository = paymasterRepository;
    }

    @Override
    public Optional<AuthUser> getAuthenticUser(String login, String password) {
        for (Validator validator : Validator.values()) {
            if (validator.isValid(login)) {
                switch (validator) {
                    case MAIL_VALIDATOR: {
                        return getAuthUserByMail(login, password);
                    }
                    case LOGIN_VALIDATOR: {
                        return getAuthUserByLogin(login, password);
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public String rememberUser(String login, UserRole role) {
        String randomId = new BigInteger(130, random).toString(32);
        rememberedUsers.put(randomId, new AuthUser(login, role));
        return randomId;
    }

    private Optional<AuthUser> getAuthUserByLogin(String login, String password) {
        Member member = memberRepository.findByLogin(login);
        if (isNotNullPasswordIdentified(member, password)) {
            return Optional.of(new AuthUser(member.getLogin(), UserRole.MEMBER));
        }
        Administrator optionalAdministrator = administratorRepository.findByLogin(login);
        if (isNotNullPasswordIdentified(optionalAdministrator, password)) {
            return Optional.of(new AuthUser(optionalAdministrator.getLogin(), UserRole.ADMINISTRATOR));
        }
        Manager optionalManager = managerRepository.findByLogin(login);
        if (isNotNullPasswordIdentified(optionalManager, password)) {
            return Optional.of(new AuthUser(optionalManager.getLogin(), UserRole.MANAGER));
        }
        Moderator optionalModerator = moderatorRepository.findByLogin(login);
        if (isNotNullPasswordIdentified(optionalModerator, password)) {
            return Optional.of(new AuthUser(optionalModerator.getLogin(), UserRole.MODERATOR));
        }
        Paymaster paymaster = paymasterRepository.findByLogin(login);
        if (isNotNullPasswordIdentified(paymaster, password)) {
            return Optional.of(new AuthUser(paymaster.getLogin(), UserRole.PAYMASTER));
        }
        return Optional.empty();
    }

    private boolean isNotNullPasswordIdentified(User user, String password) {
        return user != null && user.getPassword().equals(password);
    }

    private Optional<AuthUser> getAuthUserByMail(String mail, String password) {
        Member member = memberRepository.findByEmail(mail);
        if (isNotNullPasswordIdentified(member, password)) {
            return Optional.of(new AuthUser(member.getLogin(), UserRole.MEMBER));
        }
        Administrator optionalAdministrator = administratorRepository.findByEmail(mail);
        if (isNotNullPasswordIdentified(optionalAdministrator, password)) {
            return Optional.of(new AuthUser(optionalAdministrator.getLogin(), UserRole.ADMINISTRATOR));
        }
        Manager optionalManager = managerRepository.findByEmail(mail);
        if (isNotNullPasswordIdentified(optionalManager, password)) {
            return Optional.of(new AuthUser(optionalManager.getLogin(), UserRole.MANAGER));
        }
        Moderator optionalModerator = moderatorRepository.findByEmail(mail);
        if (isNotNullPasswordIdentified(optionalModerator, password)) {
            return Optional.of(new AuthUser(optionalModerator.getLogin(), UserRole.MODERATOR));
        }
        Paymaster paymaster = paymasterRepository.findByEmail(mail);
        if (isNotNullPasswordIdentified(paymaster, password)) {
            return Optional.of(new AuthUser(paymaster.getLogin(), UserRole.PAYMASTER));
        }
        return Optional.empty();
    }

    @Override
    public AuthUser getRememberedUser(String id) {
        return rememberedUsers.get(id);
    }

    public void removeRememberedUser(String id) {
        rememberedUsers.remove(id);
    }
}
