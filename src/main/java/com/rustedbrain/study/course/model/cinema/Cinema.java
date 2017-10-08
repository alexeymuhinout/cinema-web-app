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
    @ElementCollection
    @CollectionTable(
            name = "additionalFeature",
            joinColumns = @JoinColumn(name = "cinemaId")
    )
    private List<String> additionalFeatures;
    @OneToMany(mappedBy = "cinema")
    private List<CinemaHall> cinemaHalls;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId", referencedColumnName = "id")
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHalls;
    }

    public void setCinemaHalls(List<CinemaHall> cinemaHalls) {
        this.cinemaHalls = cinemaHalls;
    }

    public List<String> getAdditionalFeatures() {
        return additionalFeatures;
    }

    public void setAdditionalFeatures(List<String> additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Cinema cinema = (Cinema) o;

        return name.equals(cinema.name) && (filmScreenings != null ? filmScreenings.equals(cinema.filmScreenings) : cinema.filmScreenings == null) && (additionalFeatures != null ? additionalFeatures.equals(cinema.additionalFeatures) : cinema.additionalFeatures == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (filmScreenings != null ? filmScreenings.hashCode() : 0);
        result = 31 * result + (additionalFeatures != null ? additionalFeatures.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", filmScreenings=" + filmScreenings +
                ", additionalFeatures=" + additionalFeatures +
                '}';
    }
}
