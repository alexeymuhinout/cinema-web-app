package com.rustedbrain.study.course.model.authorization;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moderator")
public class Moderator extends User {
}
