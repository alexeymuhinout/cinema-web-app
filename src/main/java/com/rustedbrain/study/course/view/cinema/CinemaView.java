package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = VaadinUI.CINEMA_VIEW)
public class CinemaView extends NavigationView {

    public static final String CINEMA_ATTRIBUTE = "cinema";
    @Autowired
    CinemaService cinemaService;
    private Cinema cinema;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        cinema = (Cinema) VaadinSession.getCurrent().getAttribute(CinemaView.CINEMA_ATTRIBUTE);
        if (cinema != null) {
            layout.addComponentsAndExpand(createCinemaPanel(cinema));
        } else {
            Notification.show("Cinema is not selected");
        }
        addComponent(layout);
    }

    private Component createCinemaPanel(Cinema cinema) {
        VerticalLayout layout = new VerticalLayout();


        return new Panel(layout);
    }

    private void deleteCinema() {
        cinemaService.deleteCinema(cinema);
        Page.getCurrent().setUriFragment("!" + VaadinUI.MAIN_VIEW);
    }

}
