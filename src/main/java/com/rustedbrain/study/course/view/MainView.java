package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;

import java.util.List;

public interface MainView extends ApplicationView {

    void fillFilmScreeningsPanel(List<FilmScreening> screenings);

    void addMainViewListener(MainViewListener mainViewListener);

    void showCitySelectionPanel(List<City> cities);

    interface MainViewListener {

        void buttonDeleteCityClicked(String cityName);

        void buttonCreateCityClicked(String cityName);

        void buttonShowCinemaClicked(Cinema cinema);

        void entered();

        void comboBoxCitySelectionValueSelected(City city);

        void setView(MainView mainView);
    }
}
