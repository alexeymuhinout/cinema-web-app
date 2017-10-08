package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Row;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RowRepository extends JpaRepository<Row, Long> {
}
