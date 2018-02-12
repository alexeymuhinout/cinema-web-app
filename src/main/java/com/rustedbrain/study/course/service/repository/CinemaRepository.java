package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    @Query("select c from Cinema c where c.name = ?1")
    Cinema findByName(String name);

    @Query("select c from Cinema c where c.city.id = ?1")
    List<Cinema> getCinemasByCityId(long id);
}
