package com.rustedbrain.study.course.presenter.authentication.util;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.service.CinemaService;

public class CinemaEditPresenter {
	private final CinemaService cinemaService;

	public CinemaEditPresenter(CinemaService cinemaService) {
		this.cinemaService = cinemaService;
	}

	public void buttonSaveCinemaClicked(Cinema selectedCinema, String newCinemaName, City newCity,
			String newCinemaLocation) {
		cinemaService.editCinema(selectedCinema, newCinemaName, newCity, newCinemaLocation);
	}

	public void buttonDeleteCinemaClicked(long id) {
		cinemaService.deleteCinema(id);
	}

	public void buttonAddNewCinemaClicked(City city, String cinemaName, String location) {
		cinemaService.createCinema(city, cinemaName, location);
	}
}
