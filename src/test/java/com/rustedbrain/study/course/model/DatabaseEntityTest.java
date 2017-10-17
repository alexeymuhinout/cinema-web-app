package com.rustedbrain.study.course.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseEntityTest {

    private static DatabaseEntity databaseEntity1;
    private static DatabaseEntity databaseEntity2;
    private static DatabaseEntity databaseEntity3;

    @BeforeClass
    public static void setUpBeforeClass() {
        databaseEntity1 = new DatabaseEntity();
        databaseEntity2 = new DatabaseEntity();
        databaseEntity1.setId(1);
        databaseEntity2.setId(1);
        databaseEntity3 = new DatabaseEntity();
        databaseEntity3.setId(6);
    }

    @Test
    public void testSymmetryEquals() {
        assertTrue(databaseEntity1.equals(databaseEntity2) && databaseEntity2.equals(databaseEntity1));
    }

    @Test
    public void testNotEquals() {
        assertFalse(databaseEntity2.equals(databaseEntity3));
    }

    @Test
    public void testEqualsConsistency() {
        assertEquals(databaseEntity1.equals(databaseEntity2), databaseEntity1.equals(databaseEntity2));
        databaseEntity1.setId(5);
        assertFalse(databaseEntity1.equals(databaseEntity2));
    }

    @Test
    public void testEqualsComparisonOfNull() {
        assertEquals(false, databaseEntity2.equals(null));
    }

    @Test
    public void testEqualsReflectivity() {
        assertTrue(databaseEntity1.equals(databaseEntity1));
    }

    @Test
    public void testHashCode() {
        assertTrue(databaseEntity1.hashCode() == databaseEntity2.hashCode());
    }
}
