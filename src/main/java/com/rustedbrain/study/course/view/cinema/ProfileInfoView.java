package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.dto.UserInfo;
import com.rustedbrain.study.course.view.ApplicationView;
import com.vaadin.navigator.ViewChangeListener;
import org.springframework.beans.factory.annotation.Autowired;

public interface ProfileInfoView extends ApplicationView {

    @Autowired
    void addCinemaViewListener(Listener listener);

    void showUserInfo(UserInfo userInfo, boolean ableToEdit);

    void showNotAuthError();

    public interface Listener {

        void setView(ProfileInfoView view);

        void entered(ViewChangeListener.ViewChangeEvent event);

        void buttonAuthorizeClicked();

        void buttonRegisterClicked();
    }
}
