package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.users.LoginView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = VaadinUI.CITY_VIEW)
public class CityView extends NavigationView {

    public static final String CITY_ATTRIBUTE = "cityName";

    private CinemaService cinemaService;
    private String cityName;

    private TextField textFieldCinemaName;
    private TextField textFieldCinemaStreet;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        cityName = (String) VaadinSession.getCurrent().getAttribute(CityView.CITY_ATTRIBUTE);
        if (cityName != null && !cityName.isEmpty()) {
            City city = cinemaService.getCity(cityName);
            layout.addComponentsAndExpand(createCityPanel(city));
        } else {
            Notification.show("City is not selected");
        }
        addComponentsAndExpand(layout);
    }

    private Component createCityPanel(City city) {
        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Label label = new Label("Cinemas in " + city.getName());
        horizontalLayout.addComponentsAndExpand(label);
        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            horizontalLayout.addComponentsAndExpand(new Button("Delete City", (Button.ClickListener) event -> deleteCity()));
        }
        layout.addComponentsAndExpand(horizontalLayout);

        fillLayoutByCinemas(layout, city);

        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            layout.addComponentsAndExpand(createAdminCinemaAddingPanel(city));
        }
        return new Panel(layout);
    }

    private Panel createAdminCinemaAddingPanel(City city) {
        VerticalLayout verticalLayout = new VerticalLayout();
        Panel panel = new Panel(verticalLayout);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        textFieldCinemaName = new TextField("Name");
        horizontalLayout.addComponentsAndExpand(textFieldCinemaName);
        textFieldCinemaStreet = new TextField("Street");
        horizontalLayout.addComponentsAndExpand(textFieldCinemaStreet);
        verticalLayout.addComponentsAndExpand(horizontalLayout);
        Button buttonCreateCity = new Button("Create cinema", event -> createCinema(city));
        buttonCreateCity.setSizeFull();
        verticalLayout.addComponentsAndExpand(buttonCreateCity);
        return panel;
    }

    private void createCinema(City city) {
        try {
            cinemaService.createCinema(city, textFieldCinemaName.getValue(), textFieldCinemaStreet.getValue());
            Page.getCurrent().reload();
        } catch (IllegalArgumentException ex) {
            Notification.show(ex.getMessage(), Notification.Type.WARNING_MESSAGE);
        } catch (Exception ex) {
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void fillLayoutByCinemas(VerticalLayout layout, City city) {
        for (Cinema cinema : city.getCinemas()) {
            Button button = new Button(cinema.getName() + ", " + cinema.getLocation(), (Button.ClickListener) clickEvent -> new PageNavigator().navigateToCinemaView(getUI(), cinema.getName()));
            layout.addComponentsAndExpand(button);
        }
    }

    private void deleteCity() {
        cinemaService.deleteCityByName(cityName);
        Page.getCurrent().setUriFragment("!" + VaadinUI.MAIN_VIEW);
    }
}
