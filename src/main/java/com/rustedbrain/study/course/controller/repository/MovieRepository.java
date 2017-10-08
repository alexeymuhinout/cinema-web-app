package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
