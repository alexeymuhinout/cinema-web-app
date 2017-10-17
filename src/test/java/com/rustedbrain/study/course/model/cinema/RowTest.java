package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RowTest {

    private static Row row1;
    private static Row row2;

    private static void setSeatList(Row row) {
        List<Seat> seatList = new ArrayList<>();
        Seat seat = new Seat();
        seat.setId(1);
        seat.setClientCount(8);
        seat.setNumber(10);
        seat.setPrice(BigDecimal.TEN);
        seatList.add(seat);
        row.setSeats(seatList);
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        row1 = new Row();
        row1.setId(1);
        row1.setNumber(10);
        setSeatList(row1);

        row2 = new Row();
        row2.setId(1);
        row2.setNumber(10);
        setSeatList(row2);
    }

    @Test
    public void testEquals() {
        assertTrue(row1.equals(row2) && row2.equals(row1));
    }

    @Test
    public void testHashCode() {
        assertTrue(row1.hashCode() == row2.hashCode());
    }
}
