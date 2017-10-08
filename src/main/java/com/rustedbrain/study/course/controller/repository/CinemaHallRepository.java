package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
