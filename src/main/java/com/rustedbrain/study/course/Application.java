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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
            Row row1 = new Row(1, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row2 = new Row(2, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row3 = new Row(3, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row4 = new Row(4, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row5 = new Row(5, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row6 = new Row(6, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row7 = new Row(7, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            Row row8 = new Row(8, new HashSet<>(Arrays.asList(new Seat(1, 1, BigDecimal.valueOf(12.50)), new Seat(2, 1, BigDecimal.valueOf(12.50)), new Seat(3, 1, BigDecimal.valueOf(12.50)), new Seat(4, 1, BigDecimal.valueOf(12.50)), new Seat(5, 1, BigDecimal.valueOf(12.50)), new Seat(6, 1, BigDecimal.valueOf(12.50)), new Seat(7, 1, BigDecimal.valueOf(12.50)), new Seat(8, 1, BigDecimal.valueOf(12.50)), new Seat(9, 1, BigDecimal.valueOf(12.50)), new Seat(10, 1, BigDecimal.valueOf(12.50)))));
            cinemaHall1.setRows(new HashSet<>(Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8)));

            CinemaHall cinemaHall2 = new CinemaHall("2");
            cinemaHall2.setRows(new HashSet<>(Arrays.asList(row1, row2, row3, row4, row5, row6, row7, row8)));

            cinema5.setCinemaHalls(new HashSet<>(Arrays.asList(cinemaHall1, cinemaHall2)));

            CinemaHall cinemaHall3 = new CinemaHall("3");
            cinemaHall3.setRows(new HashSet<>(Arrays.asList(row1.clone(), row2.clone(), row3.clone(), row4.clone(), row5.clone(), row6.clone(), row7.clone(), row8.clone())));

            CinemaHall cinemaHall4 = new CinemaHall("4");
            cinemaHall4.setRows(new HashSet<>(Arrays.asList(row1.clone(), row2.clone(), row3.clone(), row4.clone(), row5.clone(), row6.clone(), row7.clone(), row8.clone())));

            cinema6.setCinemaHalls(new HashSet<>(Arrays.asList(cinemaHall3, cinemaHall4)));



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

            Set<Actor> actorSet = new HashSet<>(Arrays.asList(actor1, actor2));

            actorRepository.saveAll(actorSet);

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

            Set<Genre> genreSet = new HashSet<>(Arrays.asList(genre1, genre2, genre3, genre4));

            genreRepository.saveAll(genreSet);

            Movie movie = new Movie();
            movie.setLocalizedName("Великий Гэтсби");
            movie.setOriginalName("The Great Gatsby");
            movie.setMinAge(13);
            movie.setDescription("Ник Кэррауэй, выпускник Йельского университета, находится в санатории, где лечится от алкоголизма. Он рассказывает о человеке по имени Гэтсби, с которым его свела судьба в Нью-Йорке. Нику трудно говорить об этом, и врач предлагает ему описать эту историю на бумаге.\n" +
                    "Свой рассказ Ник начинает воспоминанием о том, как в 1922 году переехал со Среднего Запада в Нью-Йорк и арендовал дом в Уэст Эгге на Лонг-Айленде. Ник посещает роскошное поместье Тома и Дэйзи Бьюкененов. Дэйзи была троюродной сестрой Ника, а её муж, Том, некогда играл в поло в Йеле, а ныне наслаждается богатством. Дейзи знакомит Ника со своей подругой, известной гольфисткой Джордан Бейкер. За ужином внезапно начинает звонить телефон: это звонит любовница Тома, о которой всем уже давно известно.\n" +
                    "Для встреч с этой любовницей, Миртл Уилсон, женой ничего не подозревающего автомеханика Джорджа из шахтёрского района штата Нью-Йорк, Том арендует квартиру в городе. Том приглашает туда Ника, где тот также знакомится с Кэтрин, сестрой Миртл, и с четой МакКи, друзьями Миртл. Ночь заканчивается всеобщей попойкой и разбитым носом Миртл, раздражавшей Тома упоминанием имени Дэйзи.");
            movie.setRegistrationDate(date);
            movie.setLastAccessDate(date);
            movie.setReleaseDate(new Date(2013, 1, 1));
            movie.setActors(actorSet);
            movie.setGenres(genreSet);

            movieRepository.save(movie);

            Movie movie2 = new Movie();
            movie2.setLocalizedName("Терминатор ");
            movie2.setOriginalName("The Terminator");
            movie2.setMinAge(18);
            movie2.setDescription("Созданный в недалеком будущем военный компьютер Скайнет развязал ядерную войну, а затем поработил остатки человечества. Однако у людей появился великий лидер — Джон Коннор, организовавший Сопротивление и сумевший выиграть Войну. Поверженный Скайнет отправляет в прошлое робота-терминатора с целью убить мать Джона Коннора — Сару Коннор. Сара работает обычной официанткой и не подозревает о своей великой миссии. На её защиту люди посылают человека — сержанта Сопротивления Кайла Риз. Силы противников не равны. Ризу противостоит чрезвычайно мощная боевая машина, неуязвимая для огнестрельного оружия. Терминатор внешне неотличим от человека: он специально предназначен для внедрения в человеческое общество и уничтожения людей. Ризу приходится не только бороться с роботом, но и убеждать шокированную Сару, а также полицию, которая принимает его за сумасшедшего террориста. В тяжёлых испытаниях недоверие, возникшее между Сарой и Ризом, уступает место симпатии и сменяется пылкой страстью. В результате на свет появляется Джон. В финале Риз погибает, но Саре удаётся уничтожить терминатора. При этом остатки его процессора и часть металлической руки попадают к сотрудникам компании «Кибердайн Системс» — будущей создательнице Скайнет. Таким образом, попытка коррекции будущего приводит к противоположному результату — всё встаёт на свои места.");
            movie2.setRegistrationDate(date);
            movie2.setLastAccessDate(date);
            movie2.setReleaseDate(new Date(1984, 1, 1));

            movieRepository.save(movie2);

            FilmScreening filmScreening = new FilmScreening();
            filmScreening.setStartDate(new Date());
            filmScreening.setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)));
            filmScreening.setRegistrationDate(date);
            filmScreening.setLastAccessDate(date);
            filmScreening.setMovie(movie);
            filmScreening.setCinema(cinema6);

            FilmScreening filmScreening2 = new FilmScreening();
            filmScreening2.setStartDate(new Date());
            filmScreening2.setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)));
            filmScreening2.setRegistrationDate(date);
            filmScreening2.setLastAccessDate(date);
            filmScreening2.setMovie(movie2);
            filmScreening2.setCinema(cinema6);

            cinema6.setFilmScreenings(new HashSet<>(Arrays.asList(filmScreening, filmScreening2)));

            cinemaRepository.save(cinema5);
            cinemaRepository.save(cinema6);

            filmScreeningRepository.save(filmScreening);
            filmScreeningRepository.save(filmScreening2);

        };
    }
}
