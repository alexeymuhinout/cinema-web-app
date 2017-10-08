package com.rustedbrain.study.course.controller.repository;

import com.rustedbrain.study.course.model.cinema.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
