package com.rustedbrain.study.course.model.cinema;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FeatureTest {

    private static Feature feature1;
    private static Feature feature2;

    @BeforeClass
    public static void setUpBeforeClass() {
        feature1 = new Feature();
        feature2 = new Feature();
        feature1.setId(1);
        feature1.setName("FeatureFilmName");
        feature1.setFeatureDescription("Description1");
        feature2.setId(1);
        feature2.setName("FeatureFilmName");
        feature2.setFeatureDescription("Description2");
    }

    @Test
    public void testEquals() {
        assertTrue(feature1.equals(feature2) && feature2.equals(feature1));
    }

    @Test
    public void testHashCode() {
        assertTrue(feature1.hashCode() == feature2.hashCode());
    }
}
