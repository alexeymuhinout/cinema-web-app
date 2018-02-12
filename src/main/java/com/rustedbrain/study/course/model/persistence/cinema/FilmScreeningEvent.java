package com.rustedbrain.study.course.model.persistence.cinema;

import com.rustedbrain.study.course.model.persistence.DatabaseEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;


@Entity
@Table(name = "filmScreeningEvent")
public class FilmScreeningEvent extends DatabaseEntity {

    @ManyToOne
    @JoinColumn(name = "filmScreeningId")
    private FilmScreening filmScreening;
    @OneToMany(mappedBy = "event")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Set<Ticket> tickets;
    @Column(name = "time")
    private Time time;
    @ManyToOne
    private CinemaHall cinemaHall;

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
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
