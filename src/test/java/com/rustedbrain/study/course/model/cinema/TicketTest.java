package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TicketTest {

    private static Ticket ticket1;
    private static Ticket ticket2;

    @BeforeClass
    public static void setUpBeforeClass() {
        ticket1 = new Ticket();
        ticket2 = new Ticket();
        ticket1.setId(1);
        ticket1.setSeat(setUpSeat());
        setUpEvent(ticket1);
        ticket2.setId(1);
        ticket2.setSeat(setUpSeat());
        setUpEvent(ticket2);
    }

    private static Seat setUpSeat() {
        Seat seat = new Seat();
        seat.setId(11);
        seat.setClientCount(8);
        seat.setNumber(10);
        seat.setPrice(BigDecimal.TEN);
        return seat;
    }

    private static void setUpEvent(Ticket ticket) {
        FilmScreeningEvent filmScreeningEvent = new FilmScreeningEvent();
        filmScreeningEvent.setId(2);
        filmScreeningEvent.setTime(new Time(10));
        setUpCinemaHall(filmScreeningEvent);
        ticket.setEvent(filmScreeningEvent);
    }

    private static void setUpCinemaHall(FilmScreeningEvent filmScreeningEvent) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(1);
        cinemaHall.setName("CinemaHallName");
        setUpRowList(cinemaHall);
        filmScreeningEvent.setCinemaHall(cinemaHall);
    }

    private static void setUpRowList(CinemaHall cinemaHall) {
        List<Row> rowList = new ArrayList<>();
        Row row = new Row();
        rowList.add(setUpSeatList(row));
        row.setId(1);
        row.setNumber(9);
        cinemaHall.setRows(rowList);
    }

    private static Row setUpSeatList(Row row) {
        List<Seat> seatList = new ArrayList<>();
        Seat seat = new Seat();
        seat.setId(1);
        seat.setClientCount(11);
        seat.setNumber(13);
        seat.setPrice(BigDecimal.TEN);
        seatList.add(setUpSeat());
        row.setSeats(seatList);
        return row;
    }

    @Test
    public void testEquals() {
        assertTrue(ticket1.equals(ticket2) && ticket2.equals(ticket1));
    }

    @Test
    public void testHashCode() {
        assertTrue(ticket1.hashCode() == ticket2.hashCode());
    }
}
