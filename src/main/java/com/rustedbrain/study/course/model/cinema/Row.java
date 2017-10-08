package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "row")
public class Row extends DatabaseEntity {

    @Column(name = "number")
    private int number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaHallId")
    private CinemaHall cinemaHall;
    @OneToMany(mappedBy = "row")
    private List<Seat> seats;

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Row row = (Row) o;

        return number == row.number && seats.equals(row.seats);
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + seats.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Row{" +
                "number=" + number +
                ", seats=" + seats +
                '}';
    }
}
