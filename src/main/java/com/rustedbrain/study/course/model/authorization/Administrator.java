package com.rustedbrain.study.course.model.authorization;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrator")
public class Administrator extends User {

}
