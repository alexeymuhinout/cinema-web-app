package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.model.dto.UserInfo;
import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.cinema.ProfileInfoView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;

@UIScope
@SpringComponent
public class ProfileInfoViewPresenter implements ProfileInfoView.Listener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5683347947742290547L;
	private ProfileInfoView view;
	private CinemaService cinemaService;
	private AuthenticationService authenticationService;
	private UserInfo userInfo;

	@Autowired
	public ProfileInfoViewPresenter(CinemaService cinemaService, AuthenticationService authenticationService) {
		this.cinemaService = cinemaService;
		this.authenticationService = authenticationService;
	}

	@Override
	public void setView(ProfileInfoView view) {
		this.view = view;
	}

	@Override
	public void entered(ViewChangeListener.ViewChangeEvent event) {
		Optional<String> optionalId = Optional.ofNullable(event.getParameters());
		if (optionalId.isPresent()) {
			Optional<UserInfo> optionalUserInfo = authenticationService.getUserInfo(Long.parseLong(optionalId.get()));
			if (optionalUserInfo.isPresent()) {
				this.userInfo = optionalUserInfo.get();
				if (authenticationService.isAuthenticated()) {
					this.view.showUserInfo(userInfo, (UserRole.MODERATOR.equals(authenticationService.getUserRole())
							|| UserRole.ADMINISTRATOR.equals(authenticationService.getUserRole())));
				} else {
					this.view.showNotAuthError();
				}
			} else {
				this.view.showError("Movie with specified id not exist.");
			}
		} else {
			this.view.showError("Movie id not presented.");
		}
	}

	@Override
	public void buttonAuthorizeClicked() {
		new PageNavigator().navigateToLoginView();
	}

	@Override
	public void buttonRegisterClicked() {
		new PageNavigator().navigateToRegistrationView();
	}
}
