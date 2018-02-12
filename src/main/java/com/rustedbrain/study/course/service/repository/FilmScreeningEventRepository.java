package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.persistence.cinema.FilmScreeningEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmScreeningEventRepository extends JpaRepository<FilmScreeningEvent, Long> {
}
