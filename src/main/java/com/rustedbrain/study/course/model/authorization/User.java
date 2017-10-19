package com.rustedbrain.study.course.model.authorization;

import com.rustedbrain.study.course.model.DatabaseEntity;
import com.rustedbrain.study.course.model.cinema.City;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "userId")
@Table(name = "user")
public class User extends DatabaseEntity {

    @Column(name = "login", length = 64, nullable = false, unique = true)
    protected String login;
    @Column(name = "password", length = 64, nullable = false)
    protected String password;
    @Column(name = "name", length = 64)
    protected String name;
    @Column(name = "surname", length = 64)
    protected String surname;
    @Column(name = "mail", length = 64, nullable = false, unique = true)
    protected String mail;
    @Column(name = "birthday")
    protected Date birthday;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId", referencedColumnName = "id")
    protected City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        return mail.equals(user.mail);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mail.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
