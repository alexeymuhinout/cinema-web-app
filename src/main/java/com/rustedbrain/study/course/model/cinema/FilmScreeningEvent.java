package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "filmScreeningEvent")
public class FilmScreeningEvent extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmScreeningId")
    private FilmScreening filmScreening;
    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;
    @Column(name = "time")
    private Time time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaHallId", referencedColumnName = "id")
    private CinemaHall cinemaHall;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public FilmScreening getFilmScreening() {
        return filmScreening;
    }

    public void setFilmScreening(FilmScreening filmScreening) {
        this.filmScreening = filmScreening;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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
