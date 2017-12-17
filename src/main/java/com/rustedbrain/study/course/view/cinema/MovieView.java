package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = VaadinUI.MOVIE_VIEW)
public class MovieView extends NavigationView {

    public static final String MOVIE_ATTRIBUTE = "movie";
    @Autowired
    CinemaService cinemaService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponentsAndExpand(createMovieDescriptionPanel());
        layout.addComponentsAndExpand(createMovieCommentsPanel());
        addComponent(layout);
    }

    private Component createMovieCommentsPanel() {
        return null;
    }

    private Component createMovieDescriptionPanel() {
        return null;
    }
}
