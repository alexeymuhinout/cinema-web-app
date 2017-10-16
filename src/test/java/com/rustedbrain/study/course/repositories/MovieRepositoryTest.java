package com.rustedbrain.study.course.repositories;

import com.rustedbrain.study.course.controller.repository.MovieRepository;
import com.rustedbrain.study.course.model.cinema.Movie;
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
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie();
        movie.setLocalizedName("Великий Гэтсби");
        movie.setOriginalName("The Great Gatsby");
        movie.setMinAge(13);
        movie.setRegistrationDate(new Date());
    }

    @Test
    public void saveMovie() throws Exception {
        Assert.assertNotNull(repository.save(movie));
    }

    @Test
    public void deleteMovie() throws Exception {
        repository.save(movie);
        repository.delete(movie);
        Assert.assertEquals(Optional.empty(), repository.findById(movie.getId()));
    }

    @Test
    public void findMovieByOriginalName() throws Exception {
        repository.save(movie);
        Assert.assertNotNull(repository.findByOriginalName("The Great Gatsby"));
    }

    @Test
    public void findMovieByLocalizedName() throws Exception {
        repository.save(movie);
        Assert.assertNotNull(repository.findByLocalizedName("Великий Гэтсби"));
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAllInBatch();
    }
}
