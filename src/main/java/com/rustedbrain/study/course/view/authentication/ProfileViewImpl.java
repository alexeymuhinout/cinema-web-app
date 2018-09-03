package com.rustedbrain.study.course.view.authentication;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.authentication.layout.*;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringView(name = VaadinUI.PROFILE_VIEW)
public class ProfileViewImpl extends VerticalLayout implements ProfileView {

    private List<ProfileView.ViewListener> listeners = new ArrayList<>();

    private TabSheet tabSheet;
    private ProfileInfoLayout profileInfoTab;
    private ProfileEditTab profileEditTab;
    private TabSheet adminLayout;
    private TabSheet statisticsLayout;
    private Window userBlockWindow;
    private AdministrationCityPanel administrationCityPanel;
    private AdministrationCinemaPanel administrationCinemaPanel;
    private AdministrationStatisticPanel administrationStatisticPanel;
    private AdministrationCinemaHallPanel administrationCinemaHallPanel;


    @Autowired
    public ProfileViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
        tabSheet = new TabSheet();
        tabSheet.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        addComponentsAndExpand(tabSheet);
    }

    private Component createManagementTab() {
        VerticalLayout layout = new VerticalLayout();


        return layout;
    }


    private Layout getPopupLayout(AbstractComponent newValueTextField, Button.ClickListener bClickListener, String propertyKey, String propertyValue, Layout targetLayout) {
        VerticalLayout changeSurnamePopupContent = new VerticalLayout();
        changeSurnamePopupContent.addComponent(newValueTextField);
        changeSurnamePopupContent.addComponent(new Button("Change", bClickListener));
        return new HorizontalLayout(new Label(propertyKey + ": "), new PopupView(propertyValue != null ? propertyValue : "N/A", changeSurnamePopupContent));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listeners.forEach(viewListener -> viewListener.entered(event));
    }

    @Override
    @Autowired
    public void addListener(ProfileView.ViewListener listener) {
        listener.setView(this);
        listeners.add(listener);
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

    @Override
    public void addProfileInfoTab(User user) {
        if (profileInfoTab != null) {
            Component oldProfileInfoLayout = this.profileInfoTab;
            this.profileInfoTab = new ProfileInfoLayout(listeners, user);
            tabSheet.replaceComponent(oldProfileInfoLayout, profileInfoTab);
        } else {
            this.profileInfoTab = new ProfileInfoLayout(listeners, user);
            tabSheet.addTab(profileInfoTab, "Info");
        }
    }

    @Override
    public void addProfileAdminEditTab(User user, List<User> users, List<City> cities) {
        if (profileEditTab != null) {
            Component oldProfileEditLayout = this.profileEditTab;
            this.profileEditTab = new AdminProfileEditTab(listeners, user, users, cities);
            tabSheet.replaceComponent(oldProfileEditLayout, profileEditTab);
        } else {
            this.profileEditTab = new AdminProfileEditTab(listeners, user, users, cities);
            tabSheet.addTab(profileEditTab, "Edit");
        }
    }

    @Override
    public void closeUserBlockWindow() {
        if (this.userBlockWindow != null) {
            this.userBlockWindow.close();
        }
    }

    @Override
    public void addAdminProfileInfoTab(User authenticUser, List<User> users) {
        if (profileInfoTab != null) {
            Component oldProfileInfoLayout = this.profileInfoTab;
            this.profileInfoTab = new AdminProfileInfoLayout(listeners, authenticUser, users);
            tabSheet.replaceComponent(oldProfileInfoLayout, profileInfoTab);
        } else {
            this.profileInfoTab = new AdminProfileInfoLayout(listeners, authenticUser, users);
            tabSheet.addTab(profileInfoTab, "Info");
        }
    }


    @Override
    public void beforeLeave(ViewBeforeLeaveEvent event) {
        closeUserBlockWindow();
        event.navigate();
    }


    @Override
    public void showUserBlockWindow(long id, String login, UserRole userRole) {
        closeUserBlockWindow();
        userBlockWindow = new Window("Block User");
        userBlockWindow.addCloseListener((Window.CloseListener) e -> userBlockWindow = null);
        userBlockWindow.setSizeUndefined();
        userBlockWindow.setModal(true);
        userBlockWindow.setResizable(false);
        userBlockWindow.setDraggable(false);

        Label warnLabel = new Label("If you want to block user \"" + login + "\", please specify date and description");
        DateTimeField blockDateTimeField = new DateTimeField("Block date", LocalDateTime.now());
        TextArea blockDescrTextField = new TextArea("Reason of blocking");
        blockDescrTextField.setSizeFull();
        Button buttonBlock = new Button("Block", (Button.ClickListener) event -> listeners.forEach(viewListener -> viewListener.buttonBlockSubmitClicked(id, blockDateTimeField.getValue(), blockDescrTextField.getValue())));
        userBlockWindow.setContent(new VerticalLayout(warnLabel, blockDateTimeField, blockDescrTextField, buttonBlock));

        UI.getCurrent().addWindow(userBlockWindow);
    }

    @Override
    public void addAdministrationTab(User currUser, List<City> cities) {
        if (adminLayout != null) {
            TabSheet oldAdminLayout = this.adminLayout;
            this.adminLayout = createAdminTab(cities);
            tabSheet.replaceComponent(oldAdminLayout, this.adminLayout);
        } else {
            this.adminLayout = createAdminTab(cities);
            tabSheet.addTab(adminLayout, "Administration");
        }
    }

    private TabSheet createAdminTab(List<City> cities) {
        TabSheet tabSheet = new TabSheet();
        administrationCityPanel = new AdministrationCityPanel(listeners, cities);
        administrationCinemaPanel = new AdministrationCinemaPanel(listeners, cities);
        administrationCinemaHallPanel = new AdministrationCinemaHallPanel(listeners, cities);
        tabSheet.addTab(administrationCityPanel, "City");
        tabSheet.addTab(administrationCinemaPanel, "Cinema");
        tabSheet.addTab(administrationCinemaHallPanel,"Cinema Hall");
        return tabSheet;
    }


    @Override
    public void addStatisticsTab() {
        if (statisticsLayout != null) {
            TabSheet oldStatisticsLayout = this.statisticsLayout;
            this.statisticsLayout = createStatisticsTab();
            tabSheet.replaceComponent(oldStatisticsLayout, this.statisticsLayout);
        } else {
            this.statisticsLayout = createStatisticsTab();
            tabSheet.addTab(statisticsLayout, "Statistics");
        }
    }

    @Override
    public TabSheet createStatisticsTab() {
        TabSheet tabSheet = new TabSheet();
        administrationStatisticPanel = new AdministrationStatisticPanel(listeners);
        tabSheet.addTab(administrationStatisticPanel, "Ticket");
        return tabSheet;
    }

    @Override
    public void setAdminEditUserSelected(User currUser) {
        profileEditTab.setSelectedUser(currUser);
    }

    @Override
    public void setAdminInfoUserSelected(User selectedUser) {
        profileInfoTab.setSelectedUser(selectedUser);
    }

    @Override
    public void addProfileEditTab(User currUser, List<City> cities) {
        if (profileEditTab != null) {
            Component oldProfileEditLayout = this.profileEditTab;
            this.profileEditTab = new ProfileEditTab(listeners, currUser, cities);
            tabSheet.replaceComponent(oldProfileEditLayout, profileEditTab);
        } else {
            this.profileEditTab = new ProfileEditTab(listeners, currUser, cities);
            tabSheet.addTab(profileEditTab, "Edit");
        }
    }
}