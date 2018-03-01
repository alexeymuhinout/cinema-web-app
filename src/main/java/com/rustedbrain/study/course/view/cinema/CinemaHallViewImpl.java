package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.*;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@UIScope
@SpringView(name = VaadinUI.CINEMA_HALL_VIEW)
public class CinemaHallViewImpl extends VerticalLayout implements CinemaHallView {

    private static final String SELECT_SEAT_FOR_BUYING_TICKET_WARNING = "Please select seat's";

    private List<CinemaHallView.CinemaHallViewListener> cinemaHallViewListeners = new ArrayList<>();

    private Panel cinemaHallPanel;
    private List<SeatButton> seatButtonList;

    @Autowired
    public CinemaHallViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
        addComponentsAndExpand(getCinemaHallPanel());
    }

    private Panel getCinemaHallPanel() {
        if (cinemaHallPanel == null) {
            cinemaHallPanel = new Panel();
        }
        return cinemaHallPanel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        cinemaHallViewListeners.forEach(listener -> listener.entered(event));
    }

    @Override
    public void fillFilmScreeningEventPanel(FilmScreeningEvent filmScreeningEvent) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponentsAndExpand(createMovieInfoPanel(filmScreeningEvent));
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponentsAndExpand(createSeatSelectionPanel(filmScreeningEvent));
        horizontalLayout.addComponentsAndExpand(createTicketBuyPanel(filmScreeningEvent));
        verticalLayout.addComponentsAndExpand(horizontalLayout);
        cinemaHallPanel.setContent(verticalLayout);
    }

    @Override
    public void unsetSeatSelected(Seat seat) {
        Optional<SeatButton> seatButton = seatButtonList.stream().filter(button -> button.getSeat().equals(seat)).findAny();
        seatButton.ifPresent(button -> button.getButton().removeStyleName(ValoTheme.BUTTON_FRIENDLY));
    }

    @Override
    public void setSeatSelected(Seat seat) {
        Optional<SeatButton> seatButton = seatButtonList.stream().filter(button -> button.getSeat().equals(seat)).findAny();
        seatButton.ifPresent(button -> button.getButton().setStyleName(ValoTheme.BUTTON_FRIENDLY));
    }

    private Component createTicketBuyPanel(FilmScreeningEvent filmScreeningEvent) {
        VerticalLayout verticalLayout = new VerticalLayout();

//        if (VaadinSession.getCurrent().getAttribute(LoginViewImpl.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
//            Set<Ticket> tickets = filmScreeningEvent.getTickets();
//            StringBuilder stringBuilder = new StringBuilder(tickets.size());
//            for (Ticket ticket : tickets) {
//                stringBuilder.append("Seat row ").append(ticket.getSeat().getRow().getNumber()).append(", seat number ").append(ticket.getSeat().getNumber()).append(", ").append(ticket.getClientName()).append(" ").append(ticket.getClientSurname()).append("</br>");
//            }
//            verticalLayout.addComponentsAndExpand(new Label(stringBuilder.toString(), ContentMode.HTML));
//        } else {
//            Label label = new Label(SELECT_SEAT_FOR_BUYING_TICKET_WARNING, ContentMode.HTML);
//            label.setSizeFull();
//
//            Button buttonBuyTicket = new Button("Buy", (Button.ClickListener) event -> cinemaHallViewListeners.forEach(CinemaHallViewListener::buttonBuyTicketClicked));
//            buttonBuyTicket.setSizeFull();
//
//            verticalLayout.addComponentsAndExpand(label, buttonBuyTicket);
//        }
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
        seatButtonList = new ArrayList<>();
        for (Row row : new TreeSet<>(cinemaHall.getRows())) {
            HorizontalLayout rowHorizontalLayout = new HorizontalLayout();
            rowHorizontalLayout.addComponent(new Label(Integer.toString(row.getNumber())));

            for (Seat seat : new TreeSet<>(row.getSeats())) {
                Button button = new Button(Integer.toString(seat.getNumber()));
                if (filmScreeningEvent.getTickets().stream().anyMatch(ticket -> ticket.getSeat().equals(seat))) {
                    button.setStyleName(ValoTheme.BUTTON_DANGER);
                } else {
                    button.addClickListener((Button.ClickListener) event -> {
                        cinemaHallViewListeners.forEach(listener -> listener.fireSeatSelected(seat));
                    });
                }
                seatButtonList.add(new SeatButton(button, seat));
                rowHorizontalLayout.addComponent(button);
            }
            seatsVerticalLayout.addComponent(rowHorizontalLayout);
        }
        return new Panel(seatsVerticalLayout);
    }


    @Override
    public void displaySelectedSeats(List<Seat> seats) {

    }

    @Override
    @Autowired
    public void addListener(CinemaHallViewListener cinemaHallViewListener) {
        cinemaHallViewListener.setView(this);
        this.cinemaHallViewListeners.add(cinemaHallViewListener);
    }

    @Override
    public void showWarning(String message) {
        Notification.show(message, Notification.Type.WARNING_MESSAGE);
    }

    @Override
    public void showError(String message) {
        Notification.show(message, Notification.Type.ERROR_MESSAGE);
    }

    @Override
    public void reloadPage() {
        Page.getCurrent().reload();
    }

    private class SeatButton {

        private Button button;
        private Seat seat;

        SeatButton(Button button, Seat seat) {
            this.button = button;
            this.seat = seat;
        }

        public Button getButton() {
            return button;
        }

        public Seat getSeat() {
            return seat;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SeatButton that = (SeatButton) o;

            return seat.equals(that.seat);
        }

        @Override
        public int hashCode() {
            return seat.hashCode();
        }
    }
}
