package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class SeatTest {

    private static Seat seat1;
    private static Seat seat2;

    @BeforeClass
    public static void setUpBeforeClass() {
        seat1 = new Seat();
        seat1.setId(1);
        seat1.setPrice(BigDecimal.TEN);
        seat1.setNumber(20);
        seat1.setClientCount(7);

        seat2 = new Seat();
        seat2.setId(1);
        seat2.setPrice(BigDecimal.TEN);
        seat2.setNumber(20);
        seat2.setClientCount(7);
    }

    @Test
    public void testEquals() {
        assertTrue(seat1.equals(seat2) && seat2.equals(seat1));
    }

    @Test
    public void testHashCode() {
        assertTrue(seat1.hashCode() == seat2.hashCode());
    }
}
