package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "filmScreening")
public class FilmScreening extends DatabaseEntity {

    private Movie movie;
    private Date startDate;
    private List<FilmScreeningEvent> times;
    private Date endDate;

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

    public List<FilmScreeningEvent> getTimes() {
        return times;
    }

    public void setTimes(List<FilmScreeningEvent> times) {
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
