package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.AuthorizationService;
import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.authorization.Member;
import com.rustedbrain.study.course.model.cinema.*;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.users.LoginView;
import com.rustedbrain.study.course.view.util.PageNavigator;
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
    public static final String SELECTED_SEATS_ATTRIBUTE = "selected_seats";

    private static final String SELECT_SEAT_FOR_BUYING_TICKET_WARNING = "Please select seat's";

    private List<SeatSelectionListener> seatSelectionListeners = new ArrayList<>();
    private CinemaService cinemaService;
    private AuthorizationService authorizationService;
    private Set<Seat> selectedSeats;

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

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
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.addComponentsAndExpand(createMovieInfoPanel(filmScreeningEvent));
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addComponentsAndExpand(createSeatSelectionPanel(filmScreeningEvent));
            horizontalLayout.addComponentsAndExpand(createTicketBuyPanel(filmScreeningEvent));
            verticalLayout.addComponentsAndExpand(horizontalLayout);
            addComponentsAndExpand(new Panel(verticalLayout));
        }
    }

    private Component createTicketBuyPanel(FilmScreeningEvent filmScreeningEvent) {
        VerticalLayout verticalLayout = new VerticalLayout();

        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            Set<Ticket> tickets = filmScreeningEvent.getTickets();
            StringBuilder stringBuilder = new StringBuilder(tickets.size());
            for (Ticket ticket : tickets) {
                stringBuilder.append("Seat row ").append(ticket.getSeat().getRow().getNumber()).append(", seat number ").append(ticket.getSeat().getNumber()).append(", ").append(ticket.getClientName()).append(" ").append(ticket.getClientSurname()).append("</br>");
            }
            verticalLayout.addComponentsAndExpand(new Label(stringBuilder.toString(), ContentMode.HTML));
        } else {
            Label label = new Label(SELECT_SEAT_FOR_BUYING_TICKET_WARNING, ContentMode.HTML);
            label.setSizeFull();
            SeatSelectionListener seatSelectionListener = new SeatSelectionListener() {
                @Override
                public void fireSeatSelected() {
                    StringBuilder stringBuilder = new StringBuilder(selectedSeats.size());
                    for (Seat seat : selectedSeats) {
                        stringBuilder.append("Row ").append(seat.getRow().getNumber()).append(", ").append("seat ").append(seat.getNumber()).append("</br>");
                    }
                    label.setValue(stringBuilder.toString());
                }

                @Override
                public void fireSeatReleased() {
                    if (selectedSeats.isEmpty()) {
                        label.setValue(SELECT_SEAT_FOR_BUYING_TICKET_WARNING);
                    } else {
                        StringBuilder stringBuilder = new StringBuilder(selectedSeats.size());
                        for (Seat seat : selectedSeats) {
                            stringBuilder.append("Seat number: ").append(seat.getNumber()).append("</br>");
                        }
                        label.setValue(stringBuilder.toString());
                    }
                }
            };
            seatSelectionListeners.add(seatSelectionListener);

            Button buttonBuyTicket = new Button("Buy", (Button.ClickListener) event -> {
                try {
                    if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE) != null) {
                        try {
                            Member member = (Member) authorizationService.getUser((String) VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_USER_ATTRIBUTE));
                            buyTicketClicked(member, filmScreeningEvent, selectedSeats);
                            new PageNavigator().navigateToMainView(getUI());
                        } catch (ClassCastException ex) {
                            Notification.show("Administrator not able to buy tickets");
                        }
                    } else {
                        new PageNavigator().navigateToTicketUserInfo(getUI(), filmScreeningEvent.getId(), selectedSeats);
                    }
                } catch (Exception ex) {
                    Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            });
            buttonBuyTicket.setSizeFull();

            verticalLayout.addComponentsAndExpand(label, buttonBuyTicket);
        }
        return new Panel(verticalLayout);
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
                if (filmScreeningEvent.getTickets().stream().anyMatch(ticket -> ticket.getSeat().equals(seat))) {
                    button.setStyleName(ValoTheme.BUTTON_DANGER);
                } else {
                    button.addClickListener((Button.ClickListener) event -> {
                        if (!selectedSeats.contains(seat)) {
                            selectedSeats.add(seat);
                            button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                            seatSelectionListeners.forEach(SeatSelectionListener::fireSeatSelected);
                        } else {
                            selectedSeats.remove(seat);
                            button.removeStyleName(ValoTheme.BUTTON_FRIENDLY);
                            seatSelectionListeners.forEach(SeatSelectionListener::fireSeatReleased);
                        }
                    });
                }
                rowHorizontalLayout.addComponent(button);
            }
            seatsVerticalLayout.addComponent(rowHorizontalLayout);
        }

        return new Panel(seatsVerticalLayout);
    }

    private void buyTicketClicked(Member member, FilmScreeningEvent filmScreeningEvent, Set<Seat> seats) {
        List<Ticket> boughtTickets = new ArrayList<>();
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
            boughtTickets.add(ticket);
        }
        cinemaService.buyTickets(boughtTickets);
    }

    private interface SeatSelectionListener {

        void fireSeatSelected();

        void fireSeatReleased();
    }
}
