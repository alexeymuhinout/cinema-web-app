package com.rustedbrain.study.course.model.authorization;

import com.rustedbrain.study.course.model.cinema.Comment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MemberTest {

    private static Member member;

    @BeforeClass
    public static void setUpBeforeClass() {
        member = new Member();
    }

    @Test
    public void testSetGetComments() {
        Set<Comment> commentList = new HashSet<>();
        Comment first = new Comment();
        first.setMessage("first");
        Comment notFirst = new Comment();
        notFirst.setMessage("NotFirst");
        commentList.add(first);
        commentList.add(notFirst);
        member.setComments(commentList);
        assertEquals("NotFirst", member.getComments().toArray(new Comment[0])[0].getMessage());
    }

    @Test
    public void testSetPermanentlyBanned() {
        member.setPermanentlyBanned(true);
        assertNotEquals(false, member.isPermanentlyBanned());
    }
}
