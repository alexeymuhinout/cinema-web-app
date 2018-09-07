package com.rustedbrain.study.course.presenter.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.authentication.CinemaHallConstructorView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class CinemaHallConstructorViewPresenter implements CinemaHallConstructorView.ViewListener {
	private final AuthenticationService authenticationService;
	private CinemaHallConstructorView cinemaHallConstructorView;

	@Autowired
	public CinemaHallConstructorViewPresenter(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void setCinemaHallConstructorView(CinemaHallConstructorView cinemaHallConstructorView) {
		this.cinemaHallConstructorView = cinemaHallConstructorView;
	}

	@Override
	public void addButtonClicked(String rows, String seats) {

	}

	@Override
	public void entered(ViewChangeEvent event) {
		UserRole role = authenticationService.getUserRole();
		switch (role) {
		case ADMINISTRATOR: {
			addVerticalMenuComponents();
		}
			break;
		case PAYMASTER:
			addVerticalMenuComponents();

			break;
		case MANAGER:
			addVerticalMenuComponents();

			break;
		case MEMBER:
			addVerticalMenuComponents();

			break;
		case MODERATOR:
			addVerticalMenuComponents();

			break;
		case NOT_AUTHORIZED:
			cinemaHallConstructorView.showError("User not authorized.");
			break;
		}

	}

	private void addVerticalMenuComponents() {
		cinemaHallConstructorView.addVerticalMenuComponents();
	}
}
