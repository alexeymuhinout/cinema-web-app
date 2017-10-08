package com.rustedbrain.study.course.model.cinema;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {

    @Column(name = "localizedName", length = 128, nullable = false)
    private String localizedName;
    @Column(name = "originalName", length = 128, nullable = false)
    private String originalName;
    @Column(name = "releaseDate")
    private Date releaseDate;
    @ManyToMany(mappedBy = "movie")
    private List<Genre> genres;
    @Column(name = "description", length = 512)
    private String description;
    @Column(name = "description")
    private int minAge;
    @Column(name = "timeMinutes")
    private int timeMinutes;
    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;
    @OneToMany(mappedBy = "movie")
    private List<Comment> comments;

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return originalName.equals(movie.originalName) && releaseDate.equals(movie.releaseDate);
    }

    @Override
    public int hashCode() {
        int result = originalName.hashCode();
        result = 31 * result + releaseDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "localizedName='" + localizedName + '\'' +
                ", originalName='" + originalName + '\'' +
                ", releaseDate=" + releaseDate +
                ", genres=" + genres +
                '}';
    }
}
