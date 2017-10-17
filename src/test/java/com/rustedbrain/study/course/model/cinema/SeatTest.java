package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SeatTest {

    private static Seat seat1;
    private static Seat seat2;
    private static Seat seat3;

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

        seat3 = new Seat();
        seat3.setId(6);
        seat3.setPrice(BigDecimal.TEN);
        seat3.setNumber(13);
        seat3.setClientCount(66);
    }

    @Test
    public void testSymmetryEquals() {
        assertTrue(seat1.equals(seat2) && seat2.equals(seat1));
    }

    @Test
    public void testNotEquals() {
        assertFalse(seat2.equals(seat3));
    }

    @Test
    public void testEqualsConsistency() {
        assertEquals(seat1.equals(seat2), seat1.equals(seat2));
        seat1.setPrice(BigDecimal.ZERO);
        assertFalse(seat1.equals(seat2));
    }

    @Test
    public void testEqualsComparisonOfNull() {
        assertEquals(false, seat1.equals(null));
    }

    @Test
    public void testEqualsReflectivity() {
        assertTrue(seat1.equals(seat1));
    }

    @Test
    public void testHashCode() {
        assertTrue(seat1.hashCode() == seat2.hashCode());
    }
}
