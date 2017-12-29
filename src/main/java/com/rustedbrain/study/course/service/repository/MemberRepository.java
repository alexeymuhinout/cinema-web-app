package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.authorization.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.mail = ?1")
    Member getMemberByMail(String mail);

    @Query("select m from Member m where m.login = ?1")
    Member getMemberByLogin(String login);

}
