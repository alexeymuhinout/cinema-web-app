package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CinemaTest {

    private static Cinema cinema1;
    private static Cinema cinema2;

    private static void setUpCinemaCity(Cinema cinema) {
        City city = new City();
        city.setId(5);
        city.setName("CityName");
        cinema.setCity(city);
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        cinema1 = new Cinema();
        cinema2 = new Cinema();
        cinema1.setId(1);
        cinema1.setName("CinemaName");
        setUpCinemaCity(cinema1);
        cinema2.setId(1);
        cinema2.setName("CinemaName");
        setUpCinemaCity(cinema2);
    }

    @Test
    public void testEquals() {
        assertTrue(cinema1.equals(cinema2) && cinema2.equals(cinema1));
    }

    @Test
    public void testHashCode() {
        assertTrue(cinema1.hashCode() == cinema2.hashCode());
    }
}
