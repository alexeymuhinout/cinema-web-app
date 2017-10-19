package com.rustedbrain.study.course.repositories;

import com.rustedbrain.study.course.controller.repository.FilmScreeningRepository;
import com.rustedbrain.study.course.model.cinema.FilmScreening;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmScreeningRepositoryTest {

    @Autowired
    private FilmScreeningRepository repository;

    private FilmScreening filmScreening;

    @Before
    public void setUp() throws Exception {
        filmScreening = new FilmScreening();
        filmScreening.setId(1);
        filmScreening.setStartDate(new Date(2017, 10, 31));
        filmScreening.setEndDate(new Date(2017, 11, 10));
        filmScreening.setRegistrationDate(new Date());
    }

    @Test
    public void saveFilmScreening() throws Exception {
        Assert.assertNotNull(repository.save(filmScreening));
    }

    @Test
    public void deleteFilmScreening() throws Exception {
        repository.save(filmScreening);
        repository.delete(filmScreening);
        Assert.assertEquals(Optional.empty(), repository.findById(filmScreening.getId()));
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAllInBatch();
    }
}
