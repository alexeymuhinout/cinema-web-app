package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
