package com.rustedbrain.study.course.view.components;

import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.users.LoginView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;
import java.util.List;

public class Menu extends CustomComponent {

    public Menu() {
        HorizontalLayout layout = new HorizontalLayout();
        List<Button> buttons = Arrays.asList(
                createNavigationButton("Main", VaadinUI.MAIN_VIEW),
                createNavigationButton("Help", VaadinUI.HELP_VIEW),
                VaadinSession.getCurrent().getAttribute(LoginView.LOGINED_USER_ATRIBUTE) != null ? createNavigationButton("Profile", VaadinUI.PROFILE_VIEW) : createNavigationButton("Login", VaadinUI.LOGIN_VIEW),
                VaadinSession.getCurrent().getAttribute(LoginView.LOGINED_USER_ATRIBUTE) == null ? createNavigationButton("Registration", VaadinUI.REGISTRATION_VIEW) : null,
                VaadinSession.getCurrent().getAttribute(LoginView.LOGINED_USER_ATRIBUTE) != null ? logoutButton() : null
        );

        for (Button button : buttons) {
            if (button != null) {
                layout.addComponentsAndExpand(button);
            }
        }
        layout.setSizeFull();
        setCompositionRoot(layout);
    }

    private Button logoutButton() {
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