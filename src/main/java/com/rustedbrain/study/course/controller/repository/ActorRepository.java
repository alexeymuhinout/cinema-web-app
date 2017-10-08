package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
