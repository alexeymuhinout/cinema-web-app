package com.rustedbrain.study.course.view.authentication;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public interface CinemaHallConstructorView extends View {
	@Autowired
	void addListener(ViewListener viewListener);

	interface ViewListener {
		void setCinemaHallConstructorView(CinemaHallConstructorView components);

		void addButtonClicked(String rows, String seats);

		void entered(ViewChangeEvent event);
	}

	void addContent();

	void showWarning(String message);

	void showError(String message);

	void reload();

	void setCinemaHallSeatMap(Map<Integer, Integer> cinemaHallSeatMap);
}
