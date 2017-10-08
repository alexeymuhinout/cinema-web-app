package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinemaFeature")
public class CinemaFeature extends DatabaseEntity {

    @Column(name = "name", length = 32, nullable = false, unique = true, columnDefinition = "Cinema feature name")
    private String name;
    @Column(name = "description", length = 256, nullable = true, unique = true, columnDefinition = "Cinema feature description")
    private String description;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "cinemaFeatureCinema",
            joinColumns = @JoinColumn(name = "cinemaFeatureId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cinemaId", referencedColumnName = "id")
    )
    private List<Cinema> cinemas;

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CinemaFeature that = (CinemaFeature) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CinemaFeature{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
