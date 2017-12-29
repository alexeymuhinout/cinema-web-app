package com.rustedbrain.study.course;


import com.rustedbrain.study.course.model.authorization.Administrator;
import com.rustedbrain.study.course.model.authorization.Member;
import com.rustedbrain.study.course.model.cinema.*;
import com.rustedbrain.study.course.service.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(SeatRepository seatRepository, RowRepository rowRepository, CinemaHallRepository cinemaHallRepository, FilmScreeningEventRepository filmScreeningEventRepository, GenreRepository genreRepository, ActorRepository actorRepository, FilmScreeningRepository filmScreeningRepository, CinemaRepository cinemaRepository, CityRepository cityRepository, AdministratorRepository administratorRepository, MemberRepository memberRepository, MovieRepository movieRepository) {
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
            cinemaRepository.save(cinema5);
            cinemaRepository.save(cinema6);

            Set<CinemaHall> cinemaHalls1 = getTestCinemaHallSet(3, cinema5);
            Set<CinemaHall> cinemaHalls2 = getTestCinemaHallSet(5, cinema6);

            cinemaHallRepository.saveAll(cinemaHalls1);
            cinemaHallRepository.saveAll(cinemaHalls2);

            for (CinemaHall cinemaHall : cinemaHalls2) {
                cinemaHallRepository.save(cinemaHall);
                Set<Row> row = cinemaHall.getRows();
                for (Row row1 : row) {
                    rowRepository.save(row1);
                    for (Seat seat : row1.getSeats()) {
                        seatRepository.save(seat);
                    }
                }
            }


            Set<Actor> theGreatGatsbyActorSet = getActorSet(
                    "Leonardo DiCaprio",
                    "Tobey Maguire",
                    "Carey Mulligan",
                    "Joel Edgerton",
                    "Isla Fisher",
                    "Jason Clarke",
                    "Amitabh Bachchan");
            actorRepository.saveAll(theGreatGatsbyActorSet);

            Set<Actor> theTerminatorActorSet = getActorSet(
                    "Arnold Schwarzenegger",
                    "Michael Biehn",
                    "Linda Hamilton",
                    "Paul Winfield");
            actorRepository.saveAll(theTerminatorActorSet);

            Set<Genre> genreSet = getGenreSet("adventure", "comedy", "drama", "horror", "historical");
            genreRepository.saveAll(genreSet);

            Movie movie1 = new Movie();
            movie1.setLocalizedName("Великий Гэтсби");
            movie1.setOriginalName("The Great Gatsby");
            movie1.setMinAge(13);
            movie1.setDescription("Ник Кэррауэй, выпускник Йельского университета, находится в санатории, где лечится от алкоголизма. Он рассказывает о человеке по имени Гэтсби, с которым его свела судьба в Нью-Йорке. Нику трудно говорить об этом, и врач предлагает ему описать эту историю на бумаге.\n" +
                    "Свой рассказ Ник начинает воспоминанием о том, как в 1922 году переехал со Среднего Запада в Нью-Йорк и арендовал дом в Уэст Эгге на Лонг-Айленде. Ник посещает роскошное поместье Тома и Дэйзи Бьюкененов. Дэйзи была троюродной сестрой Ника, а её муж, Том, некогда играл в поло в Йеле, а ныне наслаждается богатством. Дейзи знакомит Ника со своей подругой, известной гольфисткой Джордан Бейкер. За ужином внезапно начинает звонить телефон: это звонит любовница Тома, о которой всем уже давно известно.\n" +
                    "Для встреч с этой любовницей, Миртл Уилсон, женой ничего не подозревающего автомеханика Джорджа из шахтёрского района штата Нью-Йорк, Том арендует квартиру в городе. Том приглашает туда Ника, где тот также знакомится с Кэтрин, сестрой Миртл, и с четой МакКи, друзьями Миртл. Ночь заканчивается всеобщей попойкой и разбитым носом Миртл, раздражавшей Тома упоминанием имени Дэйзи.");
            movie1.setRegistrationDate(new Date());
            movie1.setLastAccessDate(new Date());
            movie1.setReleaseDate(new Date(2013, 1, 1));
            movie1.setActors(theTerminatorActorSet);
            movie1.setGenres(genreSet);
            movie1.setPosterPath("/home/alexey/Downloads/bigi.jpg");
            movieRepository.saveAndFlush(movie1);

            Movie movie2 = new Movie();
            movie2.setLocalizedName("Терминатор ");
            movie2.setOriginalName("The Terminator");
            movie2.setMinAge(18);
            movie2.setDescription("Созданный в недалеком будущем военный компьютер Скайнет развязал ядерную войну, а затем поработил остатки человечества. Однако у людей появился великий лидер — Джон Коннор, организовавший Сопротивление и сумевший выиграть Войну. Поверженный Скайнет отправляет в прошлое робота-терминатора с целью убить мать Джона Коннора — Сару Коннор. Сара работает обычной официанткой и не подозревает о своей великой миссии. На её защиту люди посылают человека — сержанта Сопротивления Кайла Риз. Силы противников не равны. Ризу противостоит чрезвычайно мощная боевая машина, неуязвимая для огнестрельного оружия. Терминатор внешне неотличим от человека: он специально предназначен для внедрения в человеческое общество и уничтожения людей. Ризу приходится не только бороться с роботом, но и убеждать шокированную Сару, а также полицию, которая принимает его за сумасшедшего террориста. В тяжёлых испытаниях недоверие, возникшее между Сарой и Ризом, уступает место симпатии и сменяется пылкой страстью. В результате на свет появляется Джон. В финале Риз погибает, но Саре удаётся уничтожить терминатора. При этом остатки его процессора и часть металлической руки попадают к сотрудникам компании «Кибердайн Системс» — будущей создательнице Скайнет. Таким образом, попытка коррекции будущего приводит к противоположному результату — всё встаёт на свои места.");
            movie2.setRegistrationDate(new Date());
            movie2.setLastAccessDate(new Date());
            movie2.setReleaseDate(new Date(1984, 1, 1));
            movie2.setActors(theTerminatorActorSet);
            movie2.setGenres(genreSet);
            movie2.setTimeMinutes(130);
            movie2.setPosterPath("/home/alexey/Downloads/terminator.jpg");
            movieRepository.saveAndFlush(movie2);

            FilmScreening filmScreening = createFilmScreening(cinemaRepository.getOne(cinema6.getId()), movieRepository.getOne(movie1.getId()));
            FilmScreening filmScreening2 = createFilmScreening(cinemaRepository.getOne(cinema6.getId()), movieRepository.getOne(movie2.getId()));
            filmScreeningRepository.save(filmScreening);
            filmScreeningRepository.save(filmScreening2);

            FilmScreeningEvent filmScreeningEvent = createFilmScreeningEvent(filmScreeningRepository.getOne(filmScreening.getId()), (CinemaHall) cinemaHalls2.toArray()[0], new Time(14, 30, 00));
            FilmScreeningEvent filmScreeningEvent2 = createFilmScreeningEvent(filmScreeningRepository.getOne(filmScreening2.getId()), (CinemaHall) cinemaHalls2.toArray()[1], new Time(17, 30, 00));


            filmScreeningEventRepository.saveAll(Arrays.asList(filmScreeningEvent, filmScreeningEvent2));


            List<FilmScreening> filmScreenings1 = filmScreeningRepository.findAll();
            List<FilmScreeningEvent> filmScreeningsEvents = filmScreeningEventRepository.findAll();
            Set<FilmScreening> filmScreenings2 = cinemaRepository.getOne(cinema6.getId()).getFilmScreenings();
        };
    }

    private FilmScreening createFilmScreening(Cinema cinema, Movie movie) {
        FilmScreening filmScreening = new FilmScreening();
        filmScreening.setStartDate(new Date());
        filmScreening.setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)));
        filmScreening.setRegistrationDate(new Date());
        filmScreening.setLastAccessDate(new Date());
        filmScreening.setMovie(movie);
        filmScreening.setCinema(cinema);
        return filmScreening;
    }

    private FilmScreeningEvent createFilmScreeningEvent(FilmScreening filmScreening, CinemaHall cinemaHall, Time time) {
        FilmScreeningEvent filmScreeningEvent = new FilmScreeningEvent();
        filmScreeningEvent.setRegistrationDate(new Date());
        filmScreeningEvent.setLastAccessDate(new Date());
        filmScreeningEvent.setFilmScreening(filmScreening);
        filmScreeningEvent.setTime(time);
        filmScreeningEvent.setCinemaHall(cinemaHall);
        return filmScreeningEvent;
    }

    private Set<CinemaHall> getTestCinemaHallSet(int cinemaHallsCount, Cinema cinema) {
        Set<CinemaHall> cinemaHalls = new HashSet<>();
        for (int i = 1; i <= cinemaHallsCount; i++) {
            CinemaHall cinemaHall = new CinemaHall();
            cinemaHall.setCinema(cinema);
            cinemaHall.setLastAccessDate(new Date());
            cinemaHall.setRegistrationDate(new Date());
            cinemaHall.setName(String.valueOf(i));

            Set<Row> rows = new HashSet<>();
            for (int j = 1; j <= 8; j++) {
                Row row = new Row();
                row.setCinemaHall(cinemaHall);
                row.setNumber(j);
                row.setRegistrationDate(new Date());
                row.setLastAccessDate(new Date());
                Set<Seat> seats = new HashSet<>();
                for (int k = 1; k < 10; k++) {
                    Seat seat = new Seat(k, 1, 12.50);
                    seat.setRow(row);
                    seat.setLastAccessDate(new Date());
                    seat.setRegistrationDate(new Date());
                    seats.add(seat);
                }
                row.setSeats(seats);
                rows.add(row);
            }
            cinemaHall.setRows(rows);
            cinemaHalls.add(cinemaHall);
        }
        return cinemaHalls;
    }

    private Set<Genre> getGenreSet(String... genres) {
        Set<Genre> genreSet = new HashSet<>(genres.length);
        for (String genre : genres) {
            Genre newGenre = new Genre();
            newGenre.setRegistrationDate(new Date());
            newGenre.setLastAccessDate(new Date());
            newGenre.setName(genre);
            genreSet.add(newGenre);
        }
        return genreSet;
    }

    private Set<Actor> getActorSet(String... nameSurnames) {
        Set<Actor> actorSet = new HashSet<>(nameSurnames.length);
        for (String nameSurname : nameSurnames) {
            Actor actor = new Actor();
            actor.setRegistrationDate(new Date());
            actor.setLastAccessDate(new Date());
            String[] nameSurnameArray = nameSurname.split(" ");
            if (nameSurnameArray.length < 2) {
                continue;
            }
            actor.setName(nameSurnameArray[0]);
            actor.setSurname(nameSurnameArray[1]);
            actorSet.add(actor);
        }
        return actorSet;
    }
}
