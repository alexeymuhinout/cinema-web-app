package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.cinema.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
