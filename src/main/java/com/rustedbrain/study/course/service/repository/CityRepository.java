package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.cinema.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c where c.name = ?1")
    City findByName(String name);
}
