package com.rustedbrain.study.course.model.authorization;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
public class Administrator extends User {

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AdministratorRole role;

    public AdministratorRole getRole() {
        return role;
    }

    public void setRole(AdministratorRole role) {
        this.role = role;
    }
}
