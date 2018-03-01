package com.rustedbrain.study.course.service;

import com.rustedbrain.study.course.model.dto.AuthUser;
import com.rustedbrain.study.course.model.dto.UserRole;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String SESSION_USERNAME = "username";
    private static final String SESSION_USER_ROLE = "user_role";
    private static final String COOKIE_NAME = "remember-me";

    private AuthorizationUserService authorizationUserService;

    private static Optional<Cookie> getRememberMeCookie() {
        Cookie[] cookies =
                VaadinService.getCurrentRequest().getCookies();
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(COOKIE_NAME))
                .findFirst();
    }

    public UserRole getUserRole() {
        if (isAuthenticated()) {
            return UserRole.valueOf(VaadinSession.getCurrent().getAttribute(SESSION_USER_ROLE).toString());
        } else {
            return UserRole.NOT_AUTHORIZED;
        }
    }

    public String getUserLogin() {
        return VaadinSession.getCurrent().getAttribute(SESSION_USERNAME).toString();
    }

    @Override
    public boolean isAuthenticated() {
        return (VaadinSession.getCurrent()
                .getAttribute(SESSION_USERNAME) != null && VaadinSession.getCurrent()
                .getAttribute(SESSION_USER_ROLE) != null)
                || loginRememberedUser();
    }

    public void logOut() {
        Optional<Cookie> cookie = getRememberMeCookie();
        if (cookie.isPresent()) {
            String id = cookie.get().getValue();
            authorizationUserService.removeRememberedUser(id);
            deleteRememberMeCookie();
        }

        VaadinSession.getCurrent().close();
        Page.getCurrent().setLocation("");
    }

    private boolean loginRememberedUser() {
        Optional<Cookie> rememberMeCookie = getRememberMeCookie();

        if (rememberMeCookie.isPresent()) {
            String id = rememberMeCookie.get().getValue();
            AuthUser authUser = authorizationUserService.getRememberedUser(id);

            if (authUser != null) {
                VaadinSession.getCurrent()
                        .setAttribute(SESSION_USERNAME, authUser.getLogin());
                VaadinSession.getCurrent()
                        .setAttribute(SESSION_USER_ROLE, authUser.getUserRole().name());
                return true;
            }
        }
        return false;
    }

    private void rememberUser(String username, UserRole role) {
        String id = authorizationUserService.rememberUser(username, role);

        Cookie cookie = new Cookie(COOKIE_NAME, id);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30); // valid for 30 days
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    private void deleteRememberMeCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    @Override
    public boolean login(String username, String password, boolean rememberMe) {
        Optional<AuthUser> optionalUser = authorizationUserService.getAuthenticUser(username, password);
        if (optionalUser.isPresent()) {
            AuthUser user = optionalUser.get();
            VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, user.getLogin());
            VaadinSession.getCurrent().setAttribute(SESSION_USER_ROLE, user.getUserRole().name());
            if (rememberMe) {
                rememberUser(user.getLogin(), user.getUserRole());
            }
            return true;
        }
        return false;
    }

    @Autowired
    public void setAuthorizationUserService(AuthorizationUserService authorizationUserService) {
        this.authorizationUserService = authorizationUserService;
    }
}
