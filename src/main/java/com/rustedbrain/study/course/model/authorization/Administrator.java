package com.rustedbrain.study.course.model.authorization;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
public class Administrator extends User {

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AdministratorRole role;

    public Administrator() {
    }

    public Administrator(String login, String password, String mail) {
        this.login = login;
        this.password = password;
        this.mail = mail;
    }

    public AdministratorRole getRole() {
        return role;
    }

    public void setRole(AdministratorRole role) {
        this.role = role;
    }
}
