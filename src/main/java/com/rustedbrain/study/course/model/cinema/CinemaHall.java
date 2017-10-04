package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinemaHall")
public class CinemaHall extends DatabaseEntity {

    @Column(name = "name", length = 64)
    private String name;
    @OneToMany(mappedBy = "cinemaHall", cascade = CascadeType.PERSIST)
    private List<Row> rows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CinemaHall that = (CinemaHall) o;

        return name.equals(that.name) && rows.equals(that.rows);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + rows.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CinemaHall{" +
                "name='" + name + '\'' +
                ", rows=" + rows +
                '}';
    }
}
