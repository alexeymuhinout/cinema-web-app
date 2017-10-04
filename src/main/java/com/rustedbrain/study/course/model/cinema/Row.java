package com.rustedbrain.study.course.model.cinema;

import java.util.List;

public class Row {

    private int number;
    private List<Seat> seats;

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
}
