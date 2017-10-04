package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "city")
public class City extends DatabaseEntity {

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "city", cascade = CascadeType.PERSIST)
    private List<Cinema> cinemas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
