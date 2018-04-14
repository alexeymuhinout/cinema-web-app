package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.dto.UserInfo;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringView(name = VaadinUI.PROFILE_INFO_VIEW)
public class ProfileInfoViewImpl extends VerticalLayout implements ProfileInfoView {

    private List<ProfileInfoView.Listener> listeners = new ArrayList<>();
    private Panel userInfoPanel;

    @Autowired
    public ProfileInfoViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
        addComponentsAndExpand(new Panel(getUserInfoPanel()));
    }

    private Panel getUserInfoPanel() {
        if (userInfoPanel == null) {
            userInfoPanel = new Panel();
        }
        return userInfoPanel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listeners.forEach(listener -> listener.entered(event));
    }

    @Override
    @Autowired
    public void addCinemaViewListener(ProfileInfoView.Listener listener) {
        listener.setView(this);
        this.listeners.add(listener);
    }

    @Override
    public void showUserInfo(UserInfo userInfo, boolean ableToEdit) {

    }

    @Override
    public void showNotAuthError() {
        Label errorLabel = new Label("Only authorized users can see user info");
        Button buttonAuthorize = new Button("Authorize", (Button.ClickListener) event -> listeners.forEach(ProfileInfoView.Listener::buttonAuthorizeClicked));
        Button buttonRegister = new Button("Register", (Button.ClickListener) event -> listeners.forEach(ProfileInfoView.Listener::buttonRegisterClicked));
        
        getUserInfoPanel().setContent(new VerticalLayout(errorLabel, new HorizontalLayout(buttonAuthorize, buttonRegister)));
    }

    @Override
    public void showWarning(String message) {
        Notification.show(message, Notification.Type.WARNING_MESSAGE);
    }

    @Override
    public void showError(String message) {
        Notification.show(message, Notification.Type.ERROR_MESSAGE);
    }

    @Override
    public void reload() {
        Page.getCurrent().reload();
    }
}
