package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import java.util.Date;

public class Ticket extends DatabaseEntity {

    private Date soldDate;
    private int hallNumber;
    private int rowNumber;
    private int seatNumber;

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
