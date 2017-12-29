package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.cinema.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
