package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FilmScreeningEventTest {

    private static FilmScreeningEvent filmScreeningEvent1;
    private static FilmScreeningEvent filmScreeningEvent2;

    @BeforeClass
    public static void setUpBeforeClass() {
        filmScreeningEvent1 = new FilmScreeningEvent();
        filmScreeningEvent2 = new FilmScreeningEvent();
        filmScreeningEvent1.setId(1);
        filmScreeningEvent1.setTime(new Time(10));
        setUpCinemaHall(filmScreeningEvent1);
        filmScreeningEvent2.setId(1);
        filmScreeningEvent2.setTime(new Time(10));
        setUpCinemaHall(filmScreeningEvent2);
    }

    private static void setUpCinemaHall(FilmScreeningEvent filmScreeningEvent) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(2);
        cinemaHall.setName("CinemaHallName");
        setUpRowList(cinemaHall);
        filmScreeningEvent.setCinemaHall(cinemaHall);
    }

    private static void setUpRowList(CinemaHall cinemaHall) {
        List<Row> rowList = new ArrayList<>();
        Row row = new Row();
        rowList.add(setUpSeatList(row));
        row.setId(1);
        row.setNumber(8);
        cinemaHall.setRows(rowList);
    }

    private static Row setUpSeatList(Row row) {
        List<Seat> seatList = new ArrayList<>();
        Seat seat = new Seat();
        seat.setId(1);
        seat.setClientCount(10);
        seat.setNumber(13);
        seat.setPrice(BigDecimal.TEN);
        seatList.add(seat);
        row.setSeats(seatList);
        return row;
    }

    @Test
    public void testEquals() {
        assertTrue(filmScreeningEvent1.equals(filmScreeningEvent2) && filmScreeningEvent2.equals(filmScreeningEvent1));
    }

    @Test
    public void testHashCode() {
        assertTrue(filmScreeningEvent1.hashCode() == filmScreeningEvent2.hashCode());
    }
}
