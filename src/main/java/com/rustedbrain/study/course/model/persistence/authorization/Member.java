package com.rustedbrain.study.course.model.persistence.authorization;


import com.rustedbrain.study.course.model.persistence.cinema.Comment;
import com.rustedbrain.study.course.model.persistence.cinema.Ticket;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member extends User {

    @OneToMany(mappedBy = "member")
    private Set<Comment> comments;
    @OneToMany(mappedBy = "member")
    private Set<Ticket> tickets;

    public Member(String login, String password, String mail) {
        super(login, password, mail);
    }

    public Member() {
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
