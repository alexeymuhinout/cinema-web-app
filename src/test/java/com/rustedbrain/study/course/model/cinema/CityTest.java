package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CityTest {

    private static City city1;
    private static City city2;

    @BeforeClass
    public static void setUpBeforeClass() {
        city1 = new City();
        city2 = new City();
        city1.setId(1);
        city1.setName("CityName");
        city2.setId(1);
        city2.setName("CityName");
    }

    @Test
    public void testEquals() {
        assertTrue(city1.equals(city2) && city2.equals(city1));
    }

    @Test
    public void testHashCode() {
        assertTrue(city1.hashCode() == city2.hashCode());
    }
}
