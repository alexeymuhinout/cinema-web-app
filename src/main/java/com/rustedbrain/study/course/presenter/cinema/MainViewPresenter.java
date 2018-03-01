package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.MainView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class MainViewPresenter implements MainView.MainViewListener, Serializable {

    private static final Logger logger = Logger.getLogger(MainViewPresenter.class.getName());

    private MainView mainView;
    private CinemaService cinemaService;
    private AuthenticationService authenticationService;

    @Autowired
    public MainViewPresenter(CinemaService cinemaService, AuthenticationService authenticationService) {
        this.cinemaService = cinemaService;
        this.authenticationService = authenticationService;
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void characterButtonClicked(Character character) {
        List<City> cities = cinemaService.getCities();
        this.mainView.setSelectedCharacterButton(character);
        this.mainView.setSelectedCharacterCities(cities.stream().filter(city -> character.equals(city.getName().charAt(0))).collect(Collectors.toList()));
    }

    @Override
    public void cityButtonClicked(City city) {

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
        new PageNavigator().navigateToCinemaView(cinema.getId());
    }

    @Override
    public void entered() {
        mainView.fillMenuPanel(authenticationService);
        try {
            InetAddress address = InetAddress.getByName(Page.getCurrent().getWebBrowser().getAddress());
            address = InetAddress.getByName("46.149.89.61");
            Optional<City> optionalCity = cinemaService.getCityByInetAddress(address);

            if (optionalCity.isPresent()) {
                City city = optionalCity.get();
                Optional<Cinema> optionalCinema = cinemaService.getNearestCinema(address, city);
                if (optionalCinema.isPresent()) {
                    List<FilmScreening> filmScreenings = cinemaService.getTodayCinemaFilmScreenings(optionalCinema.get());
                    mainView.fillFilmScreeningsPanel(filmScreenings);
                } else {
                    new PageNavigator().navigateToCityCinemasView(city.getId());
                }
            } else {
                logger.info("City was not identified for user: " + address.getHostAddress());
                mainView.showCitySelectionPanel(cinemaService.getCities());
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error occured while binding " + MainViewPresenter.class.getSimpleName() + " to " + MainView.class.getSimpleName(), ex);
        } catch (NoSuchElementException ex) {
            logger.log(Level.INFO, "User location was not identified", ex);
            // new PageNavigator().navigateToCitiesView();
        }
    }

    @Override
    public void comboBoxCitySelectionValueSelected(City city) {
        new PageNavigator().navigateToCityCinemasView(city.getId());
    }
}
