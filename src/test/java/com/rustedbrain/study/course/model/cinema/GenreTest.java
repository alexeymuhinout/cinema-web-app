package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenreTest {

    private static Genre genre1;
    private static Genre genre2;

    @BeforeClass
    public static void setUpBeforeClass() {
        genre1 = new Genre();
        genre2 = new Genre();
        genre1.setId(1);
        genre1.setName("Horror");
        genre2.setId(1);
        genre2.setName("Horror");
    }

    @Test
    public void testEquals() {
        assertEquals(genre1.equals(genre2), genre2.equals(genre1));
    }

    @Test
    public void testHashCode() {
        assertEquals(genre1.hashCode(), genre2.hashCode());
    }
}
