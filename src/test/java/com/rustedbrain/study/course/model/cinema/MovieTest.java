package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class MovieTest {

    private static Movie movie1;
    private static Movie movie2;

    @BeforeClass
    public static void setUpDataBeforeClass() {
        movie1 = new Movie();
        movie2 = new Movie();
        movie1.setId(1);
        movie1.setOriginalName("OriginalName");
        movie1.setLocalizedName("LocalizedNameOne");
        movie1.setReleaseDate(new Date(2017, 3, 3));

        movie2.setId(1);
        movie2.setOriginalName("OriginalName");
        movie1.setLocalizedName("LocalizedNameTwo");
        movie2.setReleaseDate(new Date(2017, 3, 3));
    }

    @Test
    public void testEquals() {
        assertTrue(movie1.equals(movie2) && movie2.equals(movie1));
    }

    @Test
    public void testHashCode() {
        assertTrue(movie1.hashCode() == movie2.hashCode());
    }
}
