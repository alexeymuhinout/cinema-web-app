package com.rustedbrain.study.course.model.dto;

import com.rustedbrain.study.course.model.persistence.authorization.User;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserInfo {

	private final boolean banned;
	private final String city;
	private final String login;
	private final String name;
	private final String surname;
	private final String birthday;
	private final String registrationDate;
	private final String lastAccessDate;
	private final long id;
	private UserRole role;

	public UserInfo(User user, UserRole role) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.birthday = user.getBirthday().toInstant().atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_LOCAL_DATE);
		this.registrationDate = user.getRegistrationDate().toInstant().atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_LOCAL_DATE);
		this.lastAccessDate = user.getLastAccessDate().toInstant().atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_LOCAL_DATE);
		this.city = user.getCity().getName();
		this.banned = user.getBlockPeriod().after(new Date());
		this.role = role;
	}

	public boolean isBanned() {
		return banned;
	}

	public String getCity() {
		return city;
	}

	public String getLogin() {
		return login;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public String getLastAccessDate() {
		return lastAccessDate;
	}

	public long getId() {
		return id;
	}

	public UserRole getRole() {
		return role;
	}
}
