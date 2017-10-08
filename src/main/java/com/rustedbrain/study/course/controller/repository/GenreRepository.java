package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
