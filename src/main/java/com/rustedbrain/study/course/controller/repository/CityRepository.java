package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
