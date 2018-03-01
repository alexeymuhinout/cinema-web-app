package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.persistence.authorization.Manager;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ManagerRepository extends UserRepository<Manager> {
}
