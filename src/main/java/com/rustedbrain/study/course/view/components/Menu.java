package com.rustedbrain.study.course.view.components;

import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.users.LoginView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;
import java.util.List;

public class Menu extends CustomComponent {

    public Menu() {
        VerticalLayout verticalLayout = new VerticalLayout();

        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE) != null) {
            verticalLayout.addComponent(new Label("Welcome " + VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE)));
        }

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        List<Button> buttons = Arrays.asList(
                createNavigationButton("Main", VaadinUI.MAIN_VIEW),
                createNavigationButton("Help", VaadinUI.HELP_VIEW),
                VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE) != null ? createNavigationButton("Profile", VaadinUI.PROFILE_VIEW) : createNavigationButton("Login", VaadinUI.LOGIN_VIEW),
                VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE) != null ? null : createNavigationButton("Registration", VaadinUI.REGISTRATION_VIEW),
                VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE) != null ? createLogoutButton() : null
        );

        for (Button button : buttons) {
            if (button != null) {
                horizontalLayout.addComponentsAndExpand(button);
            }
        }

        verticalLayout.addComponentsAndExpand(horizontalLayout);

        setCompositionRoot(verticalLayout);
    }

    private Button createLogoutButton() {
        Button button = new Button("Logout", (Button.ClickListener) event -> {
            getUI().getSession().close();
            getUI().getPage().setLocation(getLogoutPath());
        });
        button.addStyleName(ValoTheme.BUTTON_LARGE);
        return button;
    }

    private String getLogoutPath() {
        return getUI().getPage().getLocation().getPath();
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption, (Button.ClickListener) event -> getUI().getNavigator().navigateTo(viewName));
        button.addStyleName(ValoTheme.BUTTON_LARGE);
        return button;
    }

}