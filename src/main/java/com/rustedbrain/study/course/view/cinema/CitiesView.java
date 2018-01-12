package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;

import java.util.List;

public interface CitiesView {

    void fillCitiesPanel(List<City> cities);

    void addCitiesViewListener(CitiesView.CitiesViewListener listener);

    interface CitiesViewListener {

        void buttonDeleteCityClicked(String cityName);

        void buttonCreateCityClicked(String cityName);

        void buttonShowCinemasClicked(Cinema cinema);
    }
}
