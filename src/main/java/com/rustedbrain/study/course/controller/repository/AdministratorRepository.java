package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.authorization.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
