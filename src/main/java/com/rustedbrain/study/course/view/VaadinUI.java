package com.rustedbrain.study.course.view;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

    public static final String MAIN_VIEW = "";
    public static final String HELP_VIEW = "help_view";
    public static final String PROFILE_VIEW = "profile_view";
    public static final String LOGIN_VIEW = "login_view";
    public static final String REGISTRATION_VIEW = "registration_view";
    public Navigator navigator;
    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addProvider(viewProvider);
    }
}