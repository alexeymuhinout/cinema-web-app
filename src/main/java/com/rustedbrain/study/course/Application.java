package com.rustedbrain.study.course;


import com.rustedbrain.study.course.controller.repository.AdministratorRepository;
import com.rustedbrain.study.course.controller.repository.CinemaRepository;
import com.rustedbrain.study.course.controller.repository.CityRepository;
import com.rustedbrain.study.course.controller.repository.MemberRepository;
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
import java.util.Arrays;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(CinemaRepository cinemaRepository, CityRepository cityRepository, AdministratorRepository administratorRepository, MemberRepository memberRepository) {
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
            cinemaHall3.setRows(Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8));

            CinemaHall cinemaHall4 = new CinemaHall("4");
            cinemaHall4.setRows(Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8));

            cinema6.setCinemaHalls(Arrays.asList(cinemaHall3, cinemaHall4));

            cinemaRepository.save(cinema5);
            cinemaRepository.save(cinema6);
        };
    }
}
