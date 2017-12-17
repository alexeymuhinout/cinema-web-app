package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;
import com.rustedbrain.study.course.model.authorization.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "city")
public class City extends DatabaseEntity {

    @Column(name = "name", length = 64, nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Cinema> cinemas;
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<User> users;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
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

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }
}
