package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.cinema.*;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.authentication.LoginViewImpl;
import com.rustedbrain.study.course.view.util.NotificationUtil;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Set;

@SpringView(name = VaadinUI.CINEMA_VIEW)
public class CinemaViewImpl extends NavigableView {

    public static final String CINEMA_ATTRIBUTE = "cinema";
    private CinemaService cinemaService;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        NotificationUtil.showAvailableMessage();
    }

    private Panel createCinemaPanel(Cinema cinema) {
        VerticalLayout cinemaPanelLayout = new VerticalLayout();

        HorizontalLayout cinemaNameLayout = new HorizontalLayout();

        Label cinemaNameLabel = new Label("Showing at a " + cinema.getName() + " now");

        cinemaNameLayout.addComponentsAndExpand(cinemaNameLabel);

        if (VaadinSession.getCurrent().getAttribute(LoginViewImpl.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            cinemaNameLayout.addComponentsAndExpand(new Button("Delete Cinema", (Button.ClickListener) event -> deleteCinema(cinema)));
        }

        cinemaPanelLayout.addComponent(cinemaNameLayout);

        Set<FilmScreening> filmScreenings = cinema.getFilmScreenings();

        if (!filmScreenings.isEmpty()) {

            HorizontalLayout filmScreeningsHorizontalLayout = new HorizontalLayout();
            for (FilmScreening filmScreening : filmScreenings) {
                VerticalLayout verticalLayout = new VerticalLayout();
                Panel panel = new Panel(verticalLayout);

                Movie movie = filmScreening.getMovie();

                Label movieNameLabel = new Label(movie.getLocalizedName() + "<br />" + "(" + movie.getOriginalName() + ", " + movie.getReleaseDate().getYear() + ")", ContentMode.HTML);
                verticalLayout.addComponent(movieNameLabel);


                FileResource resource = new FileResource(new File(movie.getPosterPath()));
                Image image = new Image("", resource);
                image.setWidth(400, Unit.PIXELS);
                image.setHeight(500, Unit.PIXELS);
                HorizontalLayout horizontalLayout = new HorizontalLayout(image);

//                for (FilmScreeningEvent filmScreeningEvent : filmScreening.getFilmScreeningEvents()) {
//                    Button buttonFilmViewTime = new Button(filmScreeningEvent.getTime().getHours() + ":" + filmScreeningEvent.getTime().getMinutes(), new Button.ClickListener() {
//                        @Override
//                        public void buttonClick(Button.ClickEvent event) {
//                            new PageNavigator().navigateToFilmScreeningTicketView(getUI(), filmScreeningEvent.getId());
//                        }
//                    });
//                    buttonFilmViewTime.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                    horizontalLayout.addComponent(buttonFilmViewTime);
//                }


                verticalLayout.addComponent(horizontalLayout);

                Accordion accordion = new Accordion();
                accordion.setHeight(100.0f, Unit.PERCENTAGE);

                StringBuilder genresStringBuilder = new StringBuilder();
                for (Genre genre : movie.getGenres()) {
                    genresStringBuilder.append(genre.getName()).append("<br />");
                }
                final Label genresLabel = new Label(genresStringBuilder.toString(), ContentMode.HTML);
                genresLabel.setWidth(100.0f, Unit.PERCENTAGE);

                final VerticalLayout accordionGenresVerticalLayout = new VerticalLayout(genresLabel);
                accordionGenresVerticalLayout.setMargin(true);

                accordion.addTab(accordionGenresVerticalLayout, "Genres");

                StringBuilder actorsStringBuilder = new StringBuilder();
                for (Actor actor : movie.getActors()) {
                    actorsStringBuilder.append(actor.getName()).append(" ").append(actor.getSurname()).append("<br />");
                }
                final Label actorsLabel = new Label(actorsStringBuilder.toString(), ContentMode.HTML);
                actorsLabel.setWidth(100.0f, Unit.PERCENTAGE);

                final VerticalLayout accordionVerticalLayout = new VerticalLayout(actorsLabel);
                accordionVerticalLayout.setMargin(true);

                accordion.addTab(accordionVerticalLayout, "Actors");

                final Label descriptionLabel = new Label(movie.getDescription());
                descriptionLabel.setWidth(100.0f, Unit.PERCENTAGE);

                final VerticalLayout descriptionVerticalLayout = new VerticalLayout(descriptionLabel);
                descriptionVerticalLayout.setMargin(true);

                accordion.addTab(descriptionVerticalLayout, "Description");

                verticalLayout.addComponentsAndExpand(accordion);
                filmScreeningsHorizontalLayout.addComponentsAndExpand(panel);
            }
            cinemaPanelLayout.addComponentsAndExpand(filmScreeningsHorizontalLayout);
        }

        return new Panel(cinemaPanelLayout);
    }

    private void deleteCinema(Cinema cinema) {
        cinemaService.deleteCinema(cinema);
        Page.getCurrent().setUriFragment("!" + VaadinUI.MAIN_VIEW);
    }
}
