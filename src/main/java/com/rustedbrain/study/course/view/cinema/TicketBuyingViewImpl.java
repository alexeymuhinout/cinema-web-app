package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.service.AuthorizationUserService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringView(name = VaadinUI.TICKET_USER_INFO_VIEW)
public class TicketBuyingViewImpl extends VerticalLayout implements View {

    private CinemaService cinemaService;
    private AuthorizationUserService authorizationUserService;
    private TextField nameTextField;
    private TextField surnameTextField;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Autowired
    public void setAuthorizationUserService(AuthorizationUserService authorizationUserService) {
        this.authorizationUserService = authorizationUserService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        Object filmScreeningEventId = VaadinSession.getCurrent().getAttribute(CinemaHallViewImpl.FILM_SCREENING_EVENT_ID_ATTRIBUTE);
//        Object selectedSeats = VaadinSession.getCurrent().getAttribute(CinemaHallViewImpl.SELECTED_SEATS_ATTRIBUTE);
//        if (filmScreeningEventId != null && selectedSeats != null) {
//
//            Panel panel = new Panel("Ticket Info");
//            panel.setSizeUndefined();
//            addComponent(panel);
//
//            FormLayout content = new FormLayout();
//            nameTextField = new TextField("Name");
//            content.addComponent(nameTextField);
//            surnameTextField = new TextField("Surname");
//            content.addComponent(surnameTextField);
//
//            Button buyTicketButton = new Button("Buy", (Button.ClickListener) event1 -> {
//                String name = nameTextField.getValue();
//                String surname = surnameTextField.getValue();
//                List<Ticket> boughtTickets = new ArrayList<>();
//                for (Seat seat : (Set<Seat>) selectedSeats) {
//                    Ticket ticket = new Ticket();
//                    ticket.setEvent(cinemaService.getFilmScreeningEvent((Long) filmScreeningEventId));
//                    ticket.setSeat(seat);
//                    ticket.setSoldDate(new Date());
//                    ticket.setLastAccessDate(new Date());
//                    ticket.setRegistrationDate(new Date());
//                    ticket.setClientName(name);
//                    ticket.setClientSurname(surname);
//                    boughtTickets.add(ticket);
//                }
//                cinemaService.buyTickets(boughtTickets);
//                new PageNavigator().navigateToMainView();
//            });
//
//            content.addComponent(buyTicketButton);
//            content.setSizeUndefined();
//            content.setMargin(true);
//            panel.setContent(content);
//            setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
//        }
    }
}
