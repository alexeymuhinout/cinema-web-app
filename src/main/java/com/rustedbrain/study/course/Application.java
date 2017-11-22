package com.rustedbrain.study.course;


import com.rustedbrain.study.course.controller.repository.AdministratorRepository;
import com.rustedbrain.study.course.controller.repository.CinemaRepository;
import com.rustedbrain.study.course.controller.repository.CityRepository;
import com.rustedbrain.study.course.controller.repository.MemberRepository;
import com.rustedbrain.study.course.model.authorization.Administrator;
import com.rustedbrain.study.course.model.authorization.Member;
import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

            cinemaRepository.save(new Cinema("SkyMall", city1));
            cinemaRepository.save(new Cinema("Komod", city2));
            cinemaRepository.save(new Cinema("Dafi IMAX", city3));
            cinemaRepository.save(new Cinema("City Center", city3));
            cinemaRepository.save(new Cinema("Victory Plaza", city3));

        };
    }
}
