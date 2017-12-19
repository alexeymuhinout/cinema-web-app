package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.*;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SpringView(name = VaadinUI.CINEMA_HALL_VIEW)
public class CinemaHallView extends NavigationView {

    public static final String FILM_SCREENING_EVENT_ID_ATTRIBUTE = "film_screening_event_id";

    private CinemaService cinemaService;
    private Set<Seat> selectedSeats;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        selectedSeats = new HashSet<>();
        Long filmScreeningEventId = (Long) VaadinSession.getCurrent().getAttribute(CinemaHallView.FILM_SCREENING_EVENT_ID_ATTRIBUTE);
        FilmScreeningEvent filmScreeningEvent = cinemaService.getFilmScreeningEvent(filmScreeningEventId);
        if (filmScreeningEvent != null) {
            VerticalLayout verticalLayout = new VerticalLayout(createMovieInfoPanel(filmScreeningEvent), createSeatSelectionPanel(filmScreeningEvent));
            addComponentsAndExpand(new Panel(verticalLayout));
        }
    }

    private Component createMovieInfoPanel(FilmScreeningEvent filmScreeningEvent) {
        Movie movie = filmScreeningEvent.getFilmScreening().getMovie();
        VerticalLayout verticalLayout = new VerticalLayout(new Label(movie.getLocalizedName() + "</br>" + "(" + movie.getOriginalName() + ")", ContentMode.HTML));
        verticalLayout.setSpacing(true);
        return new Panel(verticalLayout);
    }

    private Component createSeatSelectionPanel(FilmScreeningEvent filmScreeningEvent) {
        VerticalLayout seatsVerticalLayout = new VerticalLayout();
        CinemaHall cinemaHall = filmScreeningEvent.getCinemaHall();

        for (Row row : new TreeSet<>(cinemaHall.getRows())) {
            HorizontalLayout rowHorizontalLayout = new HorizontalLayout();
            rowHorizontalLayout.addComponent(new Label(Integer.toString(row.getNumber())));
            for (Seat seat : new TreeSet<>(row.getSeats())) {
                Button button = new Button(Integer.toString(seat.getNumber()));
                button.addClickListener((Button.ClickListener) event -> {
                    if (!selectedSeats.contains(seat)) {
                        selectedSeats.add(seat);
                        button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                    } else {
                        selectedSeats.remove(seat);
                        button.removeStyleName(ValoTheme.BUTTON_FRIENDLY);
                    }
                });
                rowHorizontalLayout.addComponent(button);
            }
            seatsVerticalLayout.addComponent(rowHorizontalLayout);
        }

        return new Panel(seatsVerticalLayout);
    }

    private void buyTicketClicled(FilmScreeningEvent filmScreeningEvent, List<Seat> seats) {
        List<Ticket> boughtTickets = new ArrayList<>();
        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setEvent(filmScreeningEvent);
            ticket.setSeat(seat);
            ticket.setSoldDate(new Date());
            ticket.setLastAccessDate(new Date());
            ticket.setRegistrationDate(new Date());
            boughtTickets.add(ticket);
        }
        cinemaService.buyTickets(boughtTickets);
    }
}
