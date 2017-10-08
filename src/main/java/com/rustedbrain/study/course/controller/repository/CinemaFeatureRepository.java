package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.CinemaFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaFeatureRepository extends JpaRepository<CinemaFeature, Long> {
}
