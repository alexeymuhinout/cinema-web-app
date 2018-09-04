package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface FilmScreeningRepository extends JpaRepository<FilmScreening, Long> {

	@Query("select fs from FilmScreening fs where fs.cinema.id = ?1 and fs.endDate > ?2")
	List<FilmScreening> getFilmScreening(long cinemaId, Date currDate);
}
