package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.AuthorizationService;
import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.Seat;
import com.rustedbrain.study.course.model.cinema.Ticket;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringView(name = VaadinUI.TICKET_USER_INFO_VIEW)
public class TicketUserInfoView extends VerticalLayout implements View {

    private CinemaService cinemaService;
    private AuthorizationService authorizationService;
    private TextField nameTextField;
    private TextField surnameTextField;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Object filmScreeningEventId = VaadinSession.getCurrent().getAttribute(CinemaHallView.FILM_SCREENING_EVENT_ID_ATTRIBUTE);
        Object selectedSeats = VaadinSession.getCurrent().getAttribute(CinemaHallView.SELECTED_SEATS_ATTRIBUTE);
        if (filmScreeningEventId != null && selectedSeats != null) {

            Panel panel = new Panel("Ticket Info");
            panel.setSizeUndefined();
            addComponent(panel);

            FormLayout content = new FormLayout();
            nameTextField = new TextField("Name");
            content.addComponent(nameTextField);
            surnameTextField = new TextField("Surname");
            content.addComponent(surnameTextField);

            Button buyTicketButton = new Button("Buy", (Button.ClickListener) event1 -> {
                String name = nameTextField.getValue();
                String surname = surnameTextField.getValue();
                List<Ticket> boughtTickets = new ArrayList<>();
                for (Seat seat : (Set<Seat>) selectedSeats) {
                    Ticket ticket = new Ticket();
                    ticket.setEvent(cinemaService.getFilmScreeningEvent((Long) filmScreeningEventId));
                    ticket.setSeat(seat);
                    ticket.setSoldDate(new Date());
                    ticket.setLastAccessDate(new Date());
                    ticket.setRegistrationDate(new Date());
                    ticket.setClientName(name);
                    ticket.setClientSurname(surname);
                    boughtTickets.add(ticket);
                }
                cinemaService.buyTickets(boughtTickets);
                new PageNavigator().navigateToMainView(getUI());
            });

            content.addComponent(buyTicketButton);
            content.setSizeUndefined();
            content.setMargin(true);
            panel.setContent(content);
            setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        }
    }
}
