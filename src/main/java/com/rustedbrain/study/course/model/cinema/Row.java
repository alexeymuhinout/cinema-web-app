package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "row")
public class Row extends DatabaseEntity {

    @Column(name = "number")
    private int number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaHallId")
    private CinemaHall cinemaHall;
    @OneToMany(mappedBy = "row", cascade = {CascadeType.ALL})
    private Set<Seat> seats;

    public Row(int number, Set<Seat> seats) {
        this.number = number;
        this.seats = seats;
    }

    public Row() {
    }

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

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
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

    @Override
    public Row clone() throws CloneNotSupportedException {
        Row clonedRow = (Row) super.clone();

        if (seats != null) {
            Set<Seat> copy = new HashSet<>(seats.size());

            for (Seat seat : seats) {
                copy.add(seat.clone());
            }

            clonedRow.setSeats(copy);
        }

        return clonedRow;
    }
}
