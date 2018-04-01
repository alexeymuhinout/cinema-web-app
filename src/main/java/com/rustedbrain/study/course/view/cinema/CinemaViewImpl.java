package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.cinema.*;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@UIScope
@SpringView(name = VaadinUI.CINEMA_VIEW)
public class CinemaViewImpl extends VerticalLayout implements CinemaView {

    private List<CinemaViewListener> listeners = new ArrayList<>();
    private Panel filmScreeningsPanel;
    private Panel cinemaPanel;
    private Panel menuPanel;
    private List<Button> dateButtons;
    private HorizontalLayout filmScreeningsHorizontalLayout;

    @Autowired
    public CinemaViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(getMenuViewPanel());
        addComponentsAndExpand(new Panel(new VerticalLayout(getCinemaPanel(), getFilmScreeningsPanel())));
    }

    private Panel getFilmScreeningsPanel() {
        if (filmScreeningsPanel == null) {
            filmScreeningsPanel = new Panel();
        }
        return filmScreeningsPanel;
    }

    private Panel getCinemaPanel() {
        if (cinemaPanel == null) {
            cinemaPanel = new Panel();
        }
        return cinemaPanel;
    }

    private Panel getMenuViewPanel() {
        if (menuPanel == null) {
            menuPanel = new Panel();
        }
        return menuPanel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listeners.forEach(listener -> listener.entered(event));
    }

    private Component createAndFillFilmScreeningsLayout(Set<FilmScreening> filmScreenings) {
        this.filmScreeningsHorizontalLayout = new HorizontalLayout();
        filmScreeningsHorizontalLayout.setMargin(true);
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

            for (FilmScreeningEvent filmScreeningEvent : filmScreening.getFilmScreeningEvents()) {
                Button buttonFilmViewTime = new Button(filmScreeningEvent.getTime().getHours() + ":" + filmScreeningEvent.getTime().getMinutes(), (Button.ClickListener) event -> listeners.forEach(listener -> listener.buttonFilmViewTimeClicked(filmScreeningEvent.getId())));
                buttonFilmViewTime.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                horizontalLayout.addComponent(buttonFilmViewTime);
            }

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
            accordion.setSizeUndefined();
            verticalLayout.addComponent(accordion);
            verticalLayout.setSizeUndefined();
            filmScreeningsHorizontalLayout.addComponent(panel);
            filmScreeningsHorizontalLayout.setSizeUndefined();
        }
        return filmScreeningsHorizontalLayout;
    }

    @Override
    @Autowired
    public void addCinemaViewListener(CinemaViewListener listener) {
        listener.setView(this);
        this.listeners.add(listener);
    }

    @Override
    public void fillCinemaPanel(Cinema cinema, UserRole role, int availableToOrderDays, int currentDay) {
        Layout cinemaLayout = createAndFillCinemaLayout(cinema, role, availableToOrderDays, currentDay);
        cinemaLayout.setSizeUndefined();
        getCinemaPanel().setContent(cinemaLayout);
    }

    @Override
    public void setFilmScreenings(Set<FilmScreening> filmScreenings) {
        Component layout = createAndFillFilmScreeningsLayout(filmScreenings);
        layout.setSizeUndefined();
        getFilmScreeningsPanel().setContent(layout);
    }

    @Override
    public void fillMenuPanel(AuthenticationService authenticationService) {
        getMenuViewPanel().setContent(new MenuComponent(authenticationService));
    }

    @Override
    public void setSelectedDay(LocalDate day) {
        Optional<Button> optionalButton = dateButtons.stream().filter(button -> button.getCaption().equals(day.toString())).findAny();
        if (optionalButton.isPresent()) {
            dateButtons.forEach(button -> button.removeStyleName(ValoTheme.BUTTON_FRIENDLY));
            optionalButton.get().setStyleName(ValoTheme.BUTTON_FRIENDLY);
        }
    }

    private Layout createAndFillCinemaLayout(Cinema cinema, UserRole role, int availableToOrderDays, int currentDay) {

        VerticalLayout cinemaNameLayout = new VerticalLayout();
        cinemaNameLayout.setMargin(false);

        Label cinemaNameLabel = new Label("Showing at a " + cinema.getName() + " now");

        cinemaNameLayout.addComponentsAndExpand(cinemaNameLabel);

        switch (role) {
            case ADMINISTRATOR: {
                VerticalLayout deletePopupContent = new VerticalLayout();
                Label label = new Label("Delete cinema?");
                deletePopupContent.addComponent(label);
                deletePopupContent.addComponent(new Button("Delete", (Button.ClickListener) event -> {
                    listeners.forEach(listener -> listener.buttonDeleteCinemaClicked(cinema.getId()));
                }));
                PopupView deletePopup = new PopupView("Delete", deletePopupContent);

                VerticalLayout renamePopupContent = new VerticalLayout();
                TextField cinemaNameTextField = new TextField("New name");
                renamePopupContent.addComponent(cinemaNameTextField);
                renamePopupContent.addComponent(new Button("Apply", (Button.ClickListener) event -> listeners.forEach(cinemaViewListener -> cinemaViewListener.buttonRenameClicked(cinemaNameTextField.getValue()))));

                // The component itself
                PopupView renamePopup = new PopupView("Rename", renamePopupContent);
                renamePopup.setSizeUndefined();
                cinemaNameLayout.addComponents(deletePopup, renamePopup);
            }
            break;
        }

        dateButtons = getDateButtons(availableToOrderDays);

        return new VerticalLayout(cinemaNameLayout, new HorizontalLayout(dateButtons.toArray(new Button[0])));
    }

    private List<Button> getDateButtons(int availableToOrderDays) {
        List<Button> dateButtons = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < availableToOrderDays; i++) {
            LocalDate day = currentDate.plusDays(i);
            Button dateButton = new Button(day.toString(), (Button.ClickListener) event -> listeners.forEach(cinemaViewListener -> cinemaViewListener.buttonDayClicked(day)));
            dateButtons.add(dateButton);
        }
        return dateButtons;
    }

    @Override
    public void showWarning(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void reloadPage() {
        Page.getCurrent().reload();
    }
}
