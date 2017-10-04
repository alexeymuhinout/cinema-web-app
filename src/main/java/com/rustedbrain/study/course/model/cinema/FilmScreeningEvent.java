package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "filmScreeningEvent")
public class FilmScreeningEvent extends DatabaseEntity {

    private FilmScreening filmScreening;
    private LocalTime time;
    private CinemaHall cinemaHall;

    public FilmScreening getFilmScreening() {
        return filmScreening;
    }

    public void setFilmScreening(FilmScreening filmScreening) {
        this.filmScreening = filmScreening;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmScreeningEvent that = (FilmScreeningEvent) o;

        return time.equals(that.time) && cinemaHall.equals(that.cinemaHall);
    }

    @Override
    public int hashCode() {
        int result = time.hashCode();
        result = 31 * result + cinemaHall.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FilmScreeningEvent{" +
                "time=" + time +
                ", cinemaHall=" + cinemaHall +
                '}';
    }
}
