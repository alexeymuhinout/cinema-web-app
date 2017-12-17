package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.Actor;
import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.FilmScreening;
import com.rustedbrain.study.course.model.cinema.Movie;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.users.LoginView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringView(name = VaadinUI.CINEMA_VIEW)
public class CinemaView extends NavigationView {

    public static final String CINEMA_ATTRIBUTE = "cinema";
    private CinemaService cinemaService;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        String cinemaName = (String) VaadinSession.getCurrent().getAttribute(CinemaView.CINEMA_ATTRIBUTE);
        if (cinemaName != null && !cinemaName.isEmpty()) {
            Cinema cinema = cinemaService.getCinema(cinemaName);
            layout.addComponentsAndExpand(createCinemaPanel(cinema));
        } else {
            Notification.show("Cinema is not selected");
        }
        addComponent(layout);
    }

    private Component createCinemaPanel(Cinema cinema) {
        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Label cinemaNameLabel = new Label(cinema.getName());

        horizontalLayout.addComponentsAndExpand(cinemaNameLabel);

        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            horizontalLayout.addComponentsAndExpand(new Button("Delete Cinema", (Button.ClickListener) event -> deleteCinema(cinema)));
        }

        layout.addComponentsAndExpand(horizontalLayout);

        List<FilmScreening> filmScreenings = cinema.getFilmScreenings();

        if (!filmScreenings.isEmpty()) {

            Accordion accordion = new Accordion();
            accordion.setHeight(100.0f, Unit.PERCENTAGE);

            for (FilmScreening filmScreening : filmScreenings) {
                Movie movie = filmScreening.getMovie();

                StringBuilder actorsStringBuilder = new StringBuilder();
                for (Actor actor : movie.getActors()) {
                    actorsStringBuilder.append(actor.getName()).append(" ").append(actor.getSurname()).append("<br />");
                }
                final Label label = new Label(actorsStringBuilder.toString(), ContentMode.HTML);
                label.setWidth(100.0f, Unit.PERCENTAGE);

                final VerticalLayout accordionVerticalLayout = new VerticalLayout(label);
                layout.setMargin(true);

                accordion.addTab(accordionVerticalLayout, "Actors");
            }

            layout.addComponentsAndExpand(accordion);
        }

        return new Panel(layout);
    }

    private void deleteCinema(Cinema cinema) {
        cinemaService.deleteCinema(cinema);
        Page.getCurrent().setUriFragment("!" + VaadinUI.MAIN_VIEW);
    }

}
