package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m from Movie m where m.localizedName = ?1")
    Movie findByLocalizedName(String localizedName);

    @Query("select m from Movie m where m.originalName = ?1")
    Movie findByOriginalName(String originalName);

}
