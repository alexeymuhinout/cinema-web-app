package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "filmScreening")
public class FilmScreening extends DatabaseEntity {

    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private Movie movie;
    @Column(name = "startDate")
    private Date startDate;
    @OneToMany(mappedBy = "filmScreening")
    @Cascade({CascadeType.ALL})
    private Set<FilmScreeningEvent> filmScreeningEvents;
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

    public Set<FilmScreeningEvent> getFilmScreeningEvents() {
        return filmScreeningEvents;
    }

    public void setFilmScreeningEvents(Set<FilmScreeningEvent> filmScreeningEvents) {
        this.filmScreeningEvents = filmScreeningEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilmScreening that = (FilmScreening) o;

        if (movie != null ? !movie.equals(that.movie) : that.movie != null) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FilmScreening{" +
                "movie=" + movie +
                ", startDate=" + startDate +
                ", filmScreeningEvents=" + filmScreeningEvents +
                ", endDate=" + endDate +
                '}';
    }
}
