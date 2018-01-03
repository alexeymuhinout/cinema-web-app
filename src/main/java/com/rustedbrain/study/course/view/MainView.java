package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.Movie;

import java.util.List;

public interface MainView extends ApplicationView {

    void fillCinemasPanel(List<Movie> movies);

    void addMainViewListener(MainViewListener mainViewListener);

    interface MainViewListener {

        void buttonDeleteCityClicked(String cityName);

        void buttonCreateCityClicked(String cityName);

        void buttonShowCinemaClicked(Cinema cinema);
    }
}
