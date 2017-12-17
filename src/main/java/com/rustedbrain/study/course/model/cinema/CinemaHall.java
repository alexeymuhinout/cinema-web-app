package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cinemaHall")
public class CinemaHall extends DatabaseEntity {

    @Column(name = "name", length = 64, nullable = false)
    private String name;
    @OneToMany(mappedBy = "cinemaHall", cascade = {CascadeType.ALL})
    private Set<Row> rows;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaId")
    private Cinema cinema;
    @OneToMany(mappedBy = "cinemaHall")
    private Set<FilmScreeningEvent> filmScreeningEvents;

    public CinemaHall(String name) {
        this.name = name;
    }

    public CinemaHall() {
    }

    public Set<FilmScreeningEvent> getFilmScreeningEvents() {
        return filmScreeningEvents;
    }

    public void setFilmScreeningEvents(Set<FilmScreeningEvent> filmScreeningEvents) {
        this.filmScreeningEvents = filmScreeningEvents;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Row> getRows() {
        return rows;
    }

    public void setRows(Set<Row> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CinemaHall that = (CinemaHall) o;

        return name.equals(that.name) && rows.equals(that.rows);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + rows.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CinemaHall{" +
                "name='" + name + '\'' +
                ", rows=" + rows +
                '}';
    }

    @Override
    public CinemaHall clone() throws CloneNotSupportedException {
        CinemaHall clonedCinemaHall = (CinemaHall) super.clone();

        if (this.rows != null) {
            Set<Row> rowsCopy = new HashSet<>(rows.size());

            for (Row row : rows) {
                rowsCopy.add(row.clone());
            }

            clonedCinemaHall.setRows(rowsCopy);
        }

        if (this.filmScreeningEvents != null) {
            Set<FilmScreeningEvent> filmScreeningEventsCopy = new HashSet<>(filmScreeningEvents.size());

            for (FilmScreeningEvent filmScreeningEvent : filmScreeningEvents) {
                filmScreeningEventsCopy.add(filmScreeningEvent.clone());
            }

            clonedCinemaHall.setFilmScreeningEvents(filmScreeningEventsCopy);
        }

        return clonedCinemaHall;
    }
}
