package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
public class Ticket extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventId")
    private FilmScreeningEvent event;
    @Column(name = "soldDate")
    private Date soldDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId")
    private Seat seat;

    public FilmScreeningEvent getEvent() {
        return event;
    }

    public void setEvent(FilmScreeningEvent event) {
        this.event = event;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ticket ticket = (Ticket) o;

        return event.equals(ticket.event) && seat.equals(ticket.seat);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + event.hashCode();
        result = 31 * result + seat.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "event=" + event +
                ", soldDate=" + soldDate +
                ", seat=" + seat +
                '}';
    }
}
