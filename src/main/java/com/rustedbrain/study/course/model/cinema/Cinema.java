package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinema")
public class Cinema extends DatabaseEntity {

    @Column(name = "name", length = 128)
    private String name;
    @OneToMany
    private List<FilmScreening> filmScreenings;
    @ManyToMany(mappedBy = "cinemas")
    private List<Feature> features;
    @OneToMany(mappedBy = "cinema", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CinemaHall> cinemaHalls;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId", referencedColumnName = "id")
    private City city;

    public Cinema() {
    }

    public Cinema(String name, City city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Cinema cinema = (Cinema) o;

        return name.equals(cinema.name) && city.equals(cinema.city);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilmScreening> getFilmScreenings() {
        return filmScreenings;
    }

    public void setFilmScreenings(List<FilmScreening> filmScreenings) {
        this.filmScreenings = filmScreenings;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHalls;
    }

    public void setCinemaHalls(List<CinemaHall> cinemaHalls) {
        this.cinemaHalls = cinemaHalls;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
