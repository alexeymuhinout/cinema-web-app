package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "filmScreening")
public class FilmScreening extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinemaId", referencedColumnName = "id")
    private Cinema cinema;
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;
    @Column(name = "startDate")
    private Date startDate;
    @OneToMany(mappedBy = "filmScreening")
    private Set<FilmScreeningEvent> times;
    @Column(name = "endDate")
    private Date endDate;

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<FilmScreeningEvent> getTimes() {
        return times;
    }

    public void setTimes(Set<FilmScreeningEvent> times) {
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmScreening that = (FilmScreening) o;

        return movie.equals(that.movie);
    }

    @Override
    public int hashCode() {
        return movie.hashCode();
    }

    @Override
    public String toString() {
        return "FilmScreening{" +
                "movie=" + movie +
                ", startDate=" + startDate +
                ", times=" + times +
                ", endDate=" + endDate +
                '}';
    }
}
