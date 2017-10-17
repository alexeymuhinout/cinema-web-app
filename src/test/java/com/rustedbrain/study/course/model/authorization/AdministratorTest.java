package com.rustedbrain.study.course.model.authorization;

import org.junit.BeforeClass;
import org.junit.Test;

import static com.rustedbrain.study.course.model.authorization.AdministratorRole.MODERATOR;
import static org.junit.Assert.*;

public class AdministratorTest {

    private static Administrator administrator;

    @BeforeClass
    public static void setUpBeforeClass() {
        administrator = new Administrator();
    }

    @Test
    public void testConstructor() {
        administrator = new Administrator("login", "password", "mail@mail.ukr");
        assertNotEquals(null, administrator);
    }

    @Test
    public void testSetRole() {
        administrator.setRole(MODERATOR);
        assertEquals(MODERATOR, administrator.getRole());
    }


}
