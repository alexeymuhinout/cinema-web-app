package com.rustedbrain.study.course.repositories;

import com.rustedbrain.study.course.controller.repository.MemberRepository;
import com.rustedbrain.study.course.model.authorization.Member;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    private Member member;

    @Before
    public void setUp() throws Exception {
        member = new Member();
        member.setRegistrationDate(new Date());
        member.setLastAccessDate(new Date());
        member.setBirthday(new Date(1995, 10, 12));
        member.setMail("member@gmail.com");
        member.setName("Member");
        member.setSurname("MemberSurname");
        member.setLogin("member123");
        member.setPassword("123321");
    }

    @Test
    public void saveMember() throws Exception {
        Assert.assertNotNull(repository.save(member));
    }

    @Test
    public void deleteMember() throws Exception {
        repository.save(member);
        repository.delete(member);
        Assert.assertEquals(Optional.empty(), repository.findById(member.getId()));
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAllInBatch();
    }

}
