package com.rustedbrain.study.course.model.authorization;


import com.rustedbrain.study.course.model.cinema.Comment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends User {

    @Column(name = "permanentlyBanned")
    private boolean permanentlyBanned;
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isPermanentlyBanned() {
        return permanentlyBanned;
    }

    public void setPermanentlyBanned(boolean permanentlyBanned) {
        this.permanentlyBanned = permanentlyBanned;
    }


}
