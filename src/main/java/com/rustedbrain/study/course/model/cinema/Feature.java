package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "feature")
public class Feature extends DatabaseEntity {

    @Column(name = "name", length = 32, nullable = false, unique = true)
    private String name;
    @Column(name = "featureDescription", length = 256, unique = true)
    private String featureDescription;
    @ManyToMany
    @JoinTable(name = "cinemaFeature",
            joinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id", referencedColumnName = "id")
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

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featuredescription) {
        this.featureDescription = featuredescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Feature that = (Feature) o;

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
        return "Feature{" +
                "name='" + name + '\'' +
                ", featureDescription='" + featureDescription + '\'' +
                ", cinemas=" + cinemas +
                "} " + super.toString();
    }
}
