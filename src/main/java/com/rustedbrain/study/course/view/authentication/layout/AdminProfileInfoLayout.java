package com.rustedbrain.study.course.view.authentication.layout;

import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.view.authentication.ProfileView;
import com.vaadin.data.HasValue;
import com.vaadin.ui.*;

import java.util.List;

public class AdminProfileInfoLayout extends ProfileInfoLayout {

	private static final long serialVersionUID = 543181990936044274L;
	private final List<User> users;

	public AdminProfileInfoLayout(List<ProfileView.ViewListener> listeners, User user, List<User> users) {
		super(listeners, user);
		this.users = users;
		layout.addComponent(new Panel(createUserSelectionLayout()), 0);
	}

	private Layout createUserSelectionLayout() {
		ComboBox<User> userComboBox = new ComboBox<>("User", users);
		userComboBox.setItemCaptionGenerator(User::getLogin);
		userComboBox.setEmptySelectionAllowed(false);
		userComboBox.setSelectedItem(user);
		userComboBox.addValueChangeListener((HasValue.ValueChangeListener<User>) event -> {
			User selectedUser = event.getValue();
			listeners.forEach(viewListener -> viewListener.comboBoxInfoUserSelected(selectedUser));
		});
		Button showMeButton = new Button("Me", (Button.ClickListener) event -> listeners
				.forEach(viewListener -> viewListener.buttonInfoShowMeClicked(users)));
		showMeButton.setSizeFull();

		HorizontalLayout layout = new HorizontalLayout(showMeButton, userComboBox);
		layout.setMargin(true);
		return layout;
	}

	@Override
	public void setSelectedUser(User selectedUser) {
		this.user = selectedUser;
		this.layout = super.createUserInfoLayout();
		this.layout.addComponent(new Panel(createUserSelectionLayout()), 0);
		setContent(new Panel(this.layout));
	}
}
