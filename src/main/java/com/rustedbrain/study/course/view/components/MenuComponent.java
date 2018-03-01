package com.rustedbrain.study.course.view.components;


import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuComponent extends CustomComponent {

    public MenuComponent(AuthenticationService authenticationService) {
        VerticalLayout verticalLayout = new VerticalLayout();

        if (authenticationService.isAuthenticated()) {
            verticalLayout.addComponent(new Label("Welcome " + authenticationService.getUserLogin()));
        }

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        createNavigationButtons(authenticationService).stream().filter(Objects::nonNull).forEach(horizontalLayout::addComponentsAndExpand);

        verticalLayout.addComponentsAndExpand(horizontalLayout);
        setCompositionRoot(verticalLayout);
    }

    private List<Button> createNavigationButtons(AuthenticationService authenticationService) {
        return Arrays.asList(
                createNavigationButton("Main", VaadinUI.MAIN_VIEW),
                createNavigationButton("Help", VaadinUI.HELP_VIEW),
                authenticationService.isAuthenticated() ? createNavigationButton("Profile", VaadinUI.PROFILE_VIEW) : createNavigationButton("Login", VaadinUI.LOGIN_VIEW),
                authenticationService.isAuthenticated() ? null : createNavigationButton("Registration", VaadinUI.REGISTRATION_VIEW),
                authenticationService.isAuthenticated() ? createLogoutButton(authenticationService) : null
        );
    }

    private Button createLogoutButton(AuthenticationService authenticationService) {
        Button button = new Button("Logout", (Button.ClickListener) event -> {
            authenticationService.logOut();
            getUI().getPage().setLocation(getLogoutPath());
        });
        button.addStyleName(ValoTheme.BUTTON_LARGE);
        return button;
    }

    private String getLogoutPath() {
        return getUI().getPage().getLocation().getPath();
    }

    private Button createNavigationButton(final String caption, final String viewName) {
        Button button = new Button(caption, (Button.ClickListener) event -> getUI().getNavigator().navigateTo(viewName));
        button.addStyleName(ValoTheme.BUTTON_LARGE);
        return button;
    }
}