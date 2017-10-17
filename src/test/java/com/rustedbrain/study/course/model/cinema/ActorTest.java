package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class ActorTest {

    private static Actor actor1;
    private static Actor actor2;

    @BeforeClass
    public static void setUpBeforeClass() {
        actor1 = new Actor();
        actor1.setId(1);
        actor1.setName("ActorName");
        actor1.setSurname("ActorSurname");

        actor2 = new Actor();
        actor2.setId(1);
        actor2.setName("ActorName");
        actor2.setSurname("ActorSurname");

    }

    @Test
    public void testEquals() {
        assertTrue(actor1.equals(actor2) && actor2.equals(actor1));
    }

    @Test
    public void testHashCode() {
        assertTrue(actor1.hashCode() == actor2.hashCode());
    }

}