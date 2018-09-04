package com.rustedbrain.study.course.presenter.authentication.util;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.service.CinemaService;
import com.vaadin.ui.TextField;

import java.util.Optional;

public class CinemaHallEditPresenter {

	private final CinemaService cinemaService;

	public CinemaHallEditPresenter(CinemaService cinemaService) {
		this.cinemaService = cinemaService;
	}

	public void buttonDeleteCinemaHallClicked(long id) {
		cinemaService.deleteCinemaHall(id);
	}

	public void buttonSaveCinemaHallClicked(CinemaHall selectedCinemaHall, String newCinemaHallName, Cinema newCinema) {
		cinemaService.editCinemaHall(selectedCinemaHall, newCinemaHallName, newCinema);

	}

	public void buttonAddNewCinemaHallClicked(String cinemaHallName, Optional<City> selectedItem,
			Optional<Cinema> selectedItem1) {

	}
}
