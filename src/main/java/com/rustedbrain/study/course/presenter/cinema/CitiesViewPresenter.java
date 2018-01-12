package com.rustedbrain.study.course.presenter.cinema;


import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.presenter.Presenter;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.cinema.CitiesView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@UIScope
@SpringComponent
public class CitiesViewPresenter implements Presenter {

    private Logger logger = Logger.getLogger(CitiesViewPresenter.class.getName());

    private CitiesView view;
    private CinemaService cinemaService;

    @Autowired
    public CitiesViewPresenter(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void bind() {
        List<City> cities = cinemaService.getCities();
        cities.sort(Comparator.comparing(City::getName));
        view.fillCitiesPanel(cities);
    }

    public void setView(CitiesView view) {
        this.view = view;
    }
}
