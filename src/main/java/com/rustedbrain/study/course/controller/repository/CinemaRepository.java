package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    @Query("select c from Cinema c where c.name = ?1")
    Cinema findByName(String name);
}
