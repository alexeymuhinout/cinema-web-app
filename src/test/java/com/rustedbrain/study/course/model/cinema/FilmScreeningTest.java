package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class FilmScreeningTest {

    private static FilmScreening filmScreening1;
    private static FilmScreening filmScreening2;

    @BeforeClass
    public static void setUpBeforeClass() {
        filmScreening1 = new FilmScreening();
        filmScreening2 = new FilmScreening();
        filmScreening1.setId(1);
        filmScreening1.setMovie(setUpMovie());
        filmScreening2.setId(1);
        filmScreening2.setMovie(setUpMovie());
    }

    private static Movie setUpMovie() {
        Movie movie = new Movie();
        movie.setId(6);
        movie.setOriginalName("OriginalName");
        movie.setLocalizedName("LocalizedNameOne");
        movie.setReleaseDate(new Date(2017, 3, 3));
        return movie;
    }

    @Test
    public void testEquals() {
        assertTrue(filmScreening1.equals(filmScreening2) && filmScreening2.equals(filmScreening1));
    }

    @Test
    public void testHashCode() {
        assertTrue(filmScreening1.hashCode() == filmScreening2.hashCode());
    }
}
