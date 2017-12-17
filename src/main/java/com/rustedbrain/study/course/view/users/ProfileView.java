package com.rustedbrain.study.course.view.users;

import com.rustedbrain.study.course.controller.service.AuthorizationService;
import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.authorization.User;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = VaadinUI.PROFILE_VIEW)
public class ProfileView extends VerticalLayout implements View {

    @Autowired
    CinemaService cinemaService;
    @Autowired
    private AuthorizationService authorizationService;

    private User user;

    private TabSheet tabSheet;

    public ProfileView() {

    }

    private Component createManagementTab() {
        VerticalLayout layout = new VerticalLayout();


        return layout;
    }

    private Component createProfileEditTab() {
        VerticalLayout layout = new VerticalLayout();

        return layout;
    }

    private Component createProfileInfoTab() {
        VerticalLayout layout = new VerticalLayout();


        Button deleteProfileButton = new Button("Delete Profile");
        deleteProfileButton.addClickListener((Button.ClickListener) event -> {
            authorizationService.deleteUser(user);
            VaadinSession.getCurrent().close();
            getUI().getPage().setLocation(getUI().getPage().getLocation().getPath());
        });

        layout.addComponent(deleteProfileButton);

        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        user = authorizationService.getUser((String) VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE));

        tabSheet = new TabSheet();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);

        tabSheet.addTab(createProfileInfoTab(), "Info");
        tabSheet.addTab(createProfileEditTab(), "Edit");
        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            tabSheet.addTab(createManagementTab(), "Management");
        }
        addComponentsAndExpand(tabSheet);
    }

}