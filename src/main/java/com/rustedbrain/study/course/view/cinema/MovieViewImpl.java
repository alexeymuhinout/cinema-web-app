package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringView(name = VaadinUI.MOVIE_VIEW)
public class MovieViewImpl extends VerticalLayout implements MovieView {

    public static final String MOVIE_ATTRIBUTE = "movie";

    @Autowired
    public MovieViewImpl(MenuComponent menuComponentView) {
        addComponentsAndExpand(menuComponentView);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponentsAndExpand(createMovieDescriptionPanel());
        layout.addComponentsAndExpand(createMovieCommentsPanel());
        addComponent(layout);
    }

    private Component createMovieCommentsPanel() {
        return new Panel();
    }

    private Component createMovieDescriptionPanel() {
        return new Panel();
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
    public void reloadPage() {
        Page.getCurrent().reload();
    }
}