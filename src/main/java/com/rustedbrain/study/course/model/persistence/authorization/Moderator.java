package com.rustedbrain.study.course.model.persistence.authorization;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moderator")
public class Moderator extends User {
}
