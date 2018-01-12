package com.rustedbrain.study.course.service.repository;

import com.rustedbrain.study.course.model.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    @Query("select u from #{#entityName} as u where u.email = ?1 ")
    T findByEmail(String email);

    @Query("select u from #{#entityName} as u where u.login = ?1 ")
    T findByLogin(String login);
}
