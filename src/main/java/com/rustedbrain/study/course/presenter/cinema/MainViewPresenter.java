package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.presenter.Presenter;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.MainView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@UIScope
@SpringComponent
public class MainViewPresenter implements Presenter, MainView.MainViewListener {

    private Logger logger = Logger.getLogger(MainViewPresenter.class.getName());

    private MainView mainView;
    private CinemaService cinemaService;

    @Autowired
    public MainViewPresenter(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void buttonDeleteCityClicked(String cityName) {
        try {
            cinemaService.deleteCity(cityName);
            mainView.reloadPage();
        } catch (IllegalArgumentException ex) {
            mainView.showWarning(ex.getMessage());
        } catch (Exception ex) {
            mainView.showError(ex.getMessage());
        }
    }

    @Override
    public void buttonCreateCityClicked(String cityName) {
        try {
            cinemaService.createCity(cityName);
            mainView.reloadPage();
        } catch (IllegalArgumentException ex) {
            mainView.showWarning(ex.getMessage());
        } catch (Exception ex) {
            mainView.showError(ex.getMessage());
        }
    }

    @Override
    public void buttonShowCinemaClicked(Cinema cinema) {
        new PageNavigator().navigateToCinemaView(cinema);
    }

    @Override
    public void bind() {
        try {

            City city = cinemaService.getCity(Page.getCurrent().getWebBrowser().getAddress());

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error occured while binding " + MainViewPresenter.class.getSimpleName() + " to " + MainView.class.getSimpleName(), ex);
        } catch (IllegalArgumentException ex) {
            logger.log(Level.INFO, "User location was not identified", ex);
        } finally {

        }
    }
}
