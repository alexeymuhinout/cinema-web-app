package com.rustedbrain.study.course;


import com.rustedbrain.study.course.controller.repository.*;
import com.rustedbrain.study.course.model.authorization.Administrator;
import com.rustedbrain.study.course.model.authorization.Member;
import com.rustedbrain.study.course.model.cinema.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(GenreRepository genreRepository, ActorRepository actorRepository, FilmScreeningRepository filmScreeningRepository, CinemaRepository cinemaRepository, CityRepository cityRepository, AdministratorRepository administratorRepository, MemberRepository memberRepository, MovieRepository movieRepository) {
        return (args) -> {
            // save a couple of customers
            administratorRepository.save(new Administrator("mogtarip", "mogtariperson1996", "mogtarmogtar@gmail.com"));
            administratorRepository.save(new Administrator("hardeathit", "hardeathit1992", "hardeathit@gmail.com"));

            memberRepository.save(new Member("Bloodar", "Bloodarkness1996", "alexeymuhinout@gmail.com"));
            memberRepository.save(new Member("eleonora", "281093", "eleonoranora@gmail.com"));


            City city1 = new City("New York");
            City city2 = new City("New Jersey");
            City city3 = new City("Washington");

            cityRepository.save(city1);
            cityRepository.save(city2);
            cityRepository.save(city3);

            Cinema cinema1 = new Cinema("Lincoln Plaza Cinema", city1);
            cinema1.setLocation("1886 Broadway");
            Cinema cinema2 = new Cinema("Village East Cinema", city1);
            cinema2.setLocation("189 2nd Ave");
            Cinema cinema3 = new Cinema("Hillsborough Cinemas", city2);
            cinema3.setLocation("111 Raider Blvd, Hillsborough Township");
            Cinema cinema4 = new Cinema("Landmark E Street Cinema", city3);
            cinema4.setLocation("555 11th St NW");
            Cinema cinema5 = new Cinema("Angelika Pop-Up at Union Market", city3);
            cinema5.setLocation("550 Penn St NE");
            Cinema cinema6 = new Cinema("AMC Mazza Gallerie", city3);
            cinema6.setLocation("5300 Wisconsin Ave NW");

            cinemaRepository.save(cinema1);
            cinemaRepository.save(cinema2);
            cinemaRepository.save(cinema3);
            cinemaRepository.save(cinema4);

            CinemaHall cinemaHall1 = new CinemaHall("1");
            Row row1 = new Row(1, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row2 = new Row(2, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row3 = new Row(3, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row4 = new Row(4, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row5 = new Row(5, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row6 = new Row(6, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row7 = new Row(7, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            Row row8 = new Row(8, Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50))));
            cinemaHall1.setRows(Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8));

            CinemaHall cinemaHall2 = new CinemaHall("2");
            cinemaHall2.setRows(Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8));

            cinema5.setCinemaHalls(Arrays.asList(cinemaHall1, cinemaHall2));

            CinemaHall cinemaHall3 = new CinemaHall("3");
            cinemaHall3.setRows(Arrays.asList(row1.clone(), row2.clone(), row3.clone(), row4.clone(), row5.clone(), row6.clone(), row7.clone(), row8.clone()));

            CinemaHall cinemaHall4 = new CinemaHall("4");
            cinemaHall4.setRows(Arrays.asList(row1.clone(), row2.clone(), row3.clone(), row4.clone(), row5.clone(), row6.clone(), row7.clone(), row8.clone()));

            cinema6.setCinemaHalls(Arrays.asList(cinemaHall3, cinemaHall4));

            cinemaRepository.save(cinema5);

            Date date = Date.from(Instant.now());

            Actor actor1 = new Actor();
            actor1.setName("Orlando");
            actor1.setSurname("Bloom");
            actor1.setRegistrationDate(date);
            actor1.setLastAccessDate(date);

            Actor actor2 = new Actor();
            actor2.setName("John");
            actor2.setSurname("Depp");
            actor2.setRegistrationDate(date);
            actor2.setLastAccessDate(date);

            actorRepository.save(actor1);
            actorRepository.save(actor2);

            Movie movie = new Movie();
            movie.setLocalizedName("Великий Гэтсби");
            movie.setOriginalName("The Great Gatsby");
            movie.setMinAge(13);
            movie.setRegistrationDate(new Date());
            movie.setActors(Arrays.asList(actor1, actor2));

            Genre genre1 = new Genre();
            genre1.setName("adventure");
            genre1.setRegistrationDate(date);
            genre1.setLastAccessDate(date);

            Genre genre2 = new Genre();
            genre2.setName("comedy");
            genre2.setRegistrationDate(date);
            genre2.setLastAccessDate(date);

            Genre genre3 = new Genre();
            genre3.setName("drama");
            genre3.setRegistrationDate(date);
            genre3.setLastAccessDate(date);

            Genre genre4 = new Genre();
            genre4.setName("horror");
            genre4.setRegistrationDate(date);
            genre4.setLastAccessDate(date);

            movie.setGenres(Arrays.asList(genre1, genre2, genre3, genre4));

            movieRepository.save(movie);

            FilmScreening filmScreening = new FilmScreening();
            filmScreening.setStartDate(new Date());
            filmScreening.setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)));
            filmScreening.setRegistrationDate(date);
            filmScreening.setLastAccessDate(date);
            filmScreening.setMovie(movie);

            filmScreeningRepository.save(filmScreening);

            cinema6.setFilmScreenings(Collections.singletonList(filmScreening));

            cinemaRepository.save(cinema6);
        };
    }
}
