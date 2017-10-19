package com.rustedbrain.study.course.model.cinema;

import com.rustedbrain.study.course.model.DatabaseEntity;
import com.rustedbrain.study.course.model.authorization.Member;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Comment extends DatabaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "movieId")
    private Movie movie;
    @Column(name = "message", length = 512, nullable = false)
    private String message;
    @Column(name = "edited")
    private boolean edited;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment1 = (Comment) o;

        return member.equals(comment1.member) && message.equals(comment1.message);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + member.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "member=" + member +
                ", movie=" + movie +
                ", message='" + message + '\'' +
                ", edited=" + edited +
                '}';
    }
}
