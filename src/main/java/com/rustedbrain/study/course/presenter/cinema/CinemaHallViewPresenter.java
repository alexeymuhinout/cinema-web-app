package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.model.persistence.authorization.Member;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreeningEvent;
import com.rustedbrain.study.course.model.persistence.cinema.Seat;
import com.rustedbrain.study.course.model.persistence.cinema.Ticket;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.cinema.CinemaHallView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

@UIScope
@SpringComponent
public class CinemaHallViewPresenter implements CinemaHallView.CinemaHallViewListener, Serializable {

    private static final Logger logger = Logger.getLogger(CinemaHallViewPresenter.class.getName());
    private final CinemaService cinemaService;
    private final AuthenticationService authenticationService;
    private CinemaHallView view;
    private Set<Seat> selectedSeats = new HashSet<>();

    @Autowired
    public CinemaHallViewPresenter(CinemaService cinemaService, AuthenticationService authenticationService) {
        this.cinemaService = cinemaService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void fireSeatSelected(Seat seat) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            view.unsetSeatSelected(seat);
        } else {
            selectedSeats.add(seat);
            view.setSeatSelected(seat);
        }
    }

    @Override
    public void setView(CinemaHallView view) {
        this.view = view;
    }

    @Override
    public void entered(ViewChangeListener.ViewChangeEvent event) {
        Optional<String> optionalId = Optional.ofNullable(event.getParameters());
        if (optionalId.isPresent()) {
            FilmScreeningEvent filmScreeningEvent = cinemaService.getFilmScreeningEvent(Long.parseLong(optionalId.get()));
            view.fillFilmScreeningEventPanel(filmScreeningEvent);
        } else {
            logger.warning("Film screening event id is not presented. Navigating to previous view...");
            // TODO navigate to previous view, what to do with params?
        }
    }

    @Override
    public void buttonBuyTicketClicked() {
        if (selectedSeats.isEmpty()) {
            view.showWarning("Please select at least one seat to buy");
        } else {
            if (authenticationService.isAuthenticated()) {
                Member member;
            } else {

            }
        }
    }

    private void buyTicketsForSeats(Member member, FilmScreeningEvent filmScreeningEvent, Set<Seat> seats) {
        List<Ticket> ticketsToBuy = new ArrayList<>();
        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setEvent(filmScreeningEvent);
            ticket.setSeat(seat);
            ticket.setSoldDate(new Date());
            ticket.setLastAccessDate(new Date());
            ticket.setRegistrationDate(new Date());
            ticket.setMember(member);
            ticket.setClientName(member.getName());
            ticket.setClientSurname(member.getSurname());
            ticketsToBuy.add(ticket);
        }
        cinemaService.buyTickets(ticketsToBuy);
    }
}
