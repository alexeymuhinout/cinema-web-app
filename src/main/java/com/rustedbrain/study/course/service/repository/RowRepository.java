package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.model.persistence.cinema.Row;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RowRepository extends JpaRepository<Row, Long> {

	@Transactional
	@Modifying
	@Query("delete from Row row where row.cinemaHall = ?1")
	void deleteCinemaHallSeats(CinemaHall cinemaHallId);
}
