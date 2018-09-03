package com.rustedbrain.study.course.view.authentication;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.presenter.authentication.util.CinemaEditPresenter;
import com.rustedbrain.study.course.presenter.authentication.util.CityEditPresenter;
import com.rustedbrain.study.course.presenter.authentication.util.CinemaHallEditPresenter;
import com.rustedbrain.study.course.view.ApplicationView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.TabSheet;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProfileView extends ApplicationView {

    @Autowired
    void addListener(ViewListener listener);

    void addProfileInfoTab(User user);

    void showUserBlockWindow(long id, String login, UserRole userRole);

    void addAdministrationTab(User currUser, List<City> cities);

    void addStatisticsTab();

    TabSheet createStatisticsTab();

    void setAdminEditUserSelected(User currentUser);

    void addProfileEditTab(User currUser, List<City> cities);

    void addProfileAdminEditTab(User currUser, List<User> users, List<City> cities);

    void closeUserBlockWindow();

    void addAdminProfileInfoTab(User authenticUser, List<User> users);

    void setAdminInfoUserSelected(User selectedUser);

    interface ViewListener {

        void entered(ViewChangeListener.ViewChangeEvent event);

        void setView(ProfileView view);

        void buttonChangeNameClicked(long id, String newName);

        void buttonChangeLoginClicked(long id, String newLogin);

        void buttonChangeMailClicked(long id, String newMail);

        void buttonChangeSurnameClicked(long id, String surname);

        void buttonChangeCityClicked(long id, long cityId);

        void buttonChangeBirthdayClicked(long id, LocalDate birthday);

        void comboBoxEditUserSelected(User currentUser);

        void buttonUnblockClicked(long id);

        void buttonBlockClicked(long id);

        void buttonEditShowMeClicked(List<User> users);

        void buttonBlockSubmitClicked(long id, LocalDateTime blockDateTime, String blockDescription);

        void comboBoxInfoUserSelected(User selectedUser);

        void buttonInfoShowMeClicked(List<User> users);

        CityEditPresenter getCityEditPresenter();

        CinemaEditPresenter getCinemaEditPresenter();

        CinemaHallEditPresenter getCinemaHallEditPresenter();
    }
}