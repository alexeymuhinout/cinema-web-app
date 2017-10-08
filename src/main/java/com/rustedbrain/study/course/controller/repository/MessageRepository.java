package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Comment, Long> {
}
