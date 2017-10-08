package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
