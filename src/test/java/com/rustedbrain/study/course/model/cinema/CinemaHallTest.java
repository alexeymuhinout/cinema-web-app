package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CinemaHallTest {

    private static CinemaHall cinemaHall1;
    private static CinemaHall cinemaHall2;

    private static Row setUpSeatList(Row row) {
        List<Seat> seatList = new ArrayList<>();
        Seat seat = new Seat();
        seat.setId(1);
        seat.setClientCount(13);
        seat.setNumber(13);
        seat.setPrice(BigDecimal.TEN);
        seatList.add(seat);
        row.setSeats(seatList);
        return  row;
    }

    private static void setUpRowList(CinemaHall cinemaHall) {
        List<Row> rowList = new ArrayList<>();
        Row row = new Row();
        rowList.add(setUpSeatList(row));
        row.setId(1);
        row.setNumber(10);
        cinemaHall.setRows(rowList);
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        cinemaHall1 = new CinemaHall();
        cinemaHall1.setId(1);
        cinemaHall1.setName("CinemaHallName");
        setUpRowList(cinemaHall1);

        cinemaHall2 = new CinemaHall();
        cinemaHall2.setId(1);
        cinemaHall2.setName("CinemaHallName");
        setUpRowList(cinemaHall2);
    }

    @Test
    public void testEquals() {
        assertTrue(cinemaHall1.equals(cinemaHall2) && cinemaHall2.equals(cinemaHall1));
    }

    @Test
    public void testHashCode() {
        assertTrue(cinemaHall1.hashCode() == cinemaHall2.hashCode());
    }
}
