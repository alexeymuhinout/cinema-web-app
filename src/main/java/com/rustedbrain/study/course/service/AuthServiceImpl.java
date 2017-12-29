package com.rustedbrain.study.course.service;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthServiceImpl {

    public static final String SESSION_USERNAME = "username";
    private static final String COOKIE_NAME = "remember-me";

    private AuthorizationUserService authorizationUserService;

    private static Optional<Cookie> getRememberMeCookie() {
        Cookie[] cookies =
                VaadinService.getCurrentRequest().getCookies();
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(COOKIE_NAME))
                .findFirst();
    }

    public boolean isAuthenticated() {
        return VaadinSession.getCurrent()
                .getAttribute(SESSION_USERNAME) != null
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
            String username = authorizationUserService.getRememberedUser(id);

            if (username != null) {
                VaadinSession.getCurrent()
                        .setAttribute(SESSION_USERNAME, username);
                return true;
            }
        }

        return false;
    }

    private void rememberUser(String username) {
        String id = authorizationUserService.rememberUser(username);

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

    public boolean login(String username, String password,
                         boolean rememberMe) {
        if (authorizationUserService.isAuthenticUser(username, password)) {
            VaadinSession.getCurrent().setAttribute(
                    SESSION_USERNAME, username);

            if (rememberMe) {
                rememberUser(username);
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
