package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaFeatureRepository extends JpaRepository<Feature, Long> {
}
