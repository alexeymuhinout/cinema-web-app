package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.authorization.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
