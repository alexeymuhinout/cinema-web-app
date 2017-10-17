package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.authorization.Member;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommentTest {

    private static Comment comment1;
    private static Comment comment2;

    @BeforeClass
    public static void setUpBeforeClass() {
        comment1 = new Comment();
        comment2 = new Comment();
        comment1.setId(1);
        comment1.setMessage("First");
        comment1.setMember(setUpMember());
        comment2.setId(1);
        comment2.setMessage("First");
        comment2.setMember(setUpMember());
    }

    private static Member setUpMember() {
        Member member = new Member();
        member.setId(2);
        member.setName("User1");
        member.setMail("user@mail.com");
        return member;
    }

    @Test
    public void testEquals() {
        assertTrue(comment1.equals(comment2) && comment2.equals(comment1));
    }

    @Test
    public void testHashCode() {
        assertTrue(comment1.hashCode() == comment2.hashCode());
    }
}
