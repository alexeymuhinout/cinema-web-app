package com.rustedbrain.study.course.model.authorization;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUser {

    private static User user1;
    private static User user2;

    @BeforeClass
    public static void setUpBeforeClass() {
        user1 = new User();
        user1.setMail("user@gmail.com");
        user1.setName("User1");
        user1.setId(1);

        user2 = new User();
        user2.setMail("user@gmail.com");
        user2.setName("User2");
        user2.setId(1);
    }

    @Test
    public void testEquals() {
        assertTrue(user1.equals(user2) && user2.equals(user1));
    }

    @Test
    public void testHashCode() {
        assertTrue(user1.hashCode() == user2.hashCode());
    }

    @Test
    public void testGetSetName() {
        user1.setSurname("Surname");
        assertEquals("Surname", user1.getSurname());
    }
}
