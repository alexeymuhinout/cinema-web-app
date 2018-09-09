package com.rustedbrain.study.course.presenter.authentication;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.exception.ResourceException;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.authentication.CinemaHallConstructorView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class CinemaHallConstructorViewPresenter implements CinemaHallConstructorView.ViewListener {
	private final AuthenticationService authenticationService;
	private final CinemaService cinemaService;
	private CinemaHallConstructorView cinemaHallConstructorView;

	private static final Logger logger = Logger.getLogger(CinemaHallConstructorViewPresenter.class.getName());

	@Autowired
	public CinemaHallConstructorViewPresenter(AuthenticationService authenticationService,
			CinemaService cinemaService) {
		this.authenticationService = authenticationService;
		this.cinemaService = cinemaService;
	}

	@Override
	public void setCinemaHallConstructorView(CinemaHallConstructorView cinemaHallConstructorView) {
		this.cinemaHallConstructorView = cinemaHallConstructorView;
	}

	@Override
	public void addButtonClicked(String rows, String seats) {
		checkRowsSeats(rows, seats);
	}

	private void checkRowsSeats(String rows, String seats) {
		try {
			int rowsInt = Integer.parseInt(rows);
			if (rowsInt <= 0) {
				cinemaHallConstructorView.showError("Rows number must be under 0.");
			}
		} catch (NumberFormatException e) {
			cinemaHallConstructorView.showError("Rows must be number.");
		}
		try {
			int seatsInt = Integer.parseInt(seats);
			if (seatsInt <= 0) {
				cinemaHallConstructorView.showError("Seats number must be under 0.");
			}
		} catch (NumberFormatException e) {
			cinemaHallConstructorView.showError("Seats must be number.");
		}
	}

	@Override
	public void entered(ViewChangeEvent event) {
		UserRole role = authenticationService.getUserRole();

		switch (role) {
		case ADMINISTRATOR: {
			addVerticalMenuComponents();
			setCinemaHallSeatMap();
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
		cinemaHallConstructorView.addContent();
	}

	private void setCinemaHallSeatMap() {
		try {
			Map<Integer, Integer> cinemaHallSeatMap = cinemaService.getCinemaHallSeatMap();
			cinemaHallConstructorView.setCinemaHallSeatMap(cinemaHallSeatMap);
		} catch (ParserConfigurationException | IOException | SAXException | ResourceException e) {
			logger.log(Level.WARNING, "Error occurred during retrieving seat map for cienma hall constructor view", e);
			cinemaHallConstructorView.showError(e.getMessage());
		}
	}
}
