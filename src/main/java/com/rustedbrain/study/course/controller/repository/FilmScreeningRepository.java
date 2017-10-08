package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.FilmScreening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmScreeningRepository extends JpaRepository<FilmScreening, Long> {
}
