package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "seat")
public class Seat extends DatabaseEntity {

    @ManyToOne
    @JoinColumn(name = "rowId", referencedColumnName = "id")
    private Row row;
    @Column(name = "number")
    private int number;
    @Column(name = "clientCount")
    private int clientCount;
    @Column(name = "price")
    private BigDecimal price;

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Seat seat = (Seat) o;

        return number == seat.number && clientCount == seat.clientCount && price.equals(seat.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + number;
        result = 31 * result + clientCount;
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "number=" + number +
                ", clientCount=" + clientCount +
                ", price=" + price +
                '}';
    }
}
