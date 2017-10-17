package com.rustedbrain.study.course.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class DatabaseEntityTest {

    private static DatabaseEntity databaseEntity1;
    private static DatabaseEntity databaseEntity2;

    @BeforeClass
    public static void setUpBeforeClass() {
        databaseEntity1 = new DatabaseEntity();
        databaseEntity2 = new DatabaseEntity();

        databaseEntity1.setId(1);
        databaseEntity1.setRegistrationDate(new Date());
        databaseEntity2.setId(1);
        databaseEntity1.setRegistrationDate(new Date());
    }

    @Test
    public void testEquals() {
        assertTrue(databaseEntity1.equals(databaseEntity2) && databaseEntity2.equals(databaseEntity1));
    }

    @Test
    public void testHashCode() {
        assertTrue(databaseEntity1.hashCode() == databaseEntity2.hashCode());
    }
}
