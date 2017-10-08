package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from city c where c.name = :name")
    City findByName(@Param("name") String name);
}
