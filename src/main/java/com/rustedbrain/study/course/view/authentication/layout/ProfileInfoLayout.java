package com.rustedbrain.study.course.view.authentication.layout;

import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.view.authentication.ProfileView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class ProfileInfoLayout extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4517194847633579500L;
	protected List<ProfileView.ViewListener> listeners;
	protected User user;
	protected VerticalLayout layout;

	public ProfileInfoLayout(List<ProfileView.ViewListener> listeners, User user) {
		this.listeners = listeners;
		this.user = user;
		setContent(new Panel(createUserInfoLayout()));
	}

	public final VerticalLayout createUserInfoLayout() {
		this.layout = new VerticalLayout();

		layout.addComponent(new Label("Login: " + user.getLogin()));
		layout.addComponent(new Label("Name: " + (user.getName() != null ? user.getName() : "N/A")));
		layout.addComponent(new Label("Surname: " + (user.getSurname() != null ? user.getSurname() : "N/A")));
		layout.addComponent(new Label("Mail: " + user.getEmail()));
		layout.addComponent(new Label("City: " + (user.getCity() != null ? user.getCity().getName() : "N/A")));
		layout.addComponent(new Label("Birthday: " + (user.getBirthday() != null ? user.getBirthday() : "N/A")));
		layout.addComponent(new Label("Last login: " + user.getLastAccessDate()));
		layout.addComponent(new Label("Profile created: " + user.getRegistrationDate()));
		return layout;
	}

	public void setSelectedUser(User selectedUser) {
		this.user = selectedUser;
		setContent(createUserInfoLayout());
	}
}
