package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
