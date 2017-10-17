package com.rustedbrain.study.course.model.authorization;

import com.rustedbrain.study.course.model.cinema.Comment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MemberTest {

    private static Member member;

    @BeforeClass
    public static void setUpBeforeClass() {
        member = new Member();
    }

    @Test
    public void testSetGetComments() {
        List<Comment> commentList = new ArrayList<>();
        Comment first = new Comment();
        first.setMessage("first");
        Comment notFirst = new Comment();
        notFirst.setMessage("NotFirst");
        commentList.add(first);
        commentList.add(notFirst);
        member.setComments(commentList);
        assertEquals("NotFirst", member.getComments().get(1).getMessage());
    }

    @Test
    public void testSetPermanentlyBanned() {
        member.setPermanentlyBanned(true);
        assertNotEquals(false, member.isPermanentlyBanned());
    }
}
