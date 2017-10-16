package com.rustedbrain.study.course.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class DatabaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "registrationDate")
    private Date registrationDate;
    @Column(name = "lastAccessDate")
    private Date lastAccessDate;

    public DatabaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseEntity that = (DatabaseEntity) o;

        return registrationDate.equals(that.registrationDate);
    }

    @Override
    public int hashCode() {
        return registrationDate.hashCode();
    }

    @Override
    public String toString() {
        return "DatabaseEntity{" +
                "id=" + id +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
