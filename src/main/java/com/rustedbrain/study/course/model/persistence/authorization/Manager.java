package com.rustedbrain.study.course.model.persistence.authorization;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "manager")
public class Manager extends User {

    @OneToMany(mappedBy = "manager")
    private Set<Cinema> cinemas;

    public Manager(String login, String password, String mail, Set<Cinema> cinemas) {
        super(login, password, mail);
        this.cinemas = cinemas;
    }

    public Manager(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }
}