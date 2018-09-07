package com.rustedbrain.study.course.view.authentication;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import org.springframework.beans.factory.annotation.Autowired;

public interface CinemaHallConstructorView extends View {
	@Autowired
	void addListener(ViewListener viewListener);

	interface ViewListener {
		void setCinemaHallConstructorView(CinemaHallConstructorView components);

		void addButtonClicked(String rows, String seats);

		void entered(ViewChangeEvent event);
	}

	void addVerticalMenuComponents();

	void showWarning(String message);

	void showError(String message);

	void reload();
}
