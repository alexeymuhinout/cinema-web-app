package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringView(name = VaadinUI.CITY_VIEW)
public class CityViewImpl extends VerticalLayout implements CityView {

    public static final String CITY_ATTRIBUTE = "cityName";


    private TextField textFieldCinemaName;
    private TextField textFieldCinemaStreet;

    @Autowired
    public CityViewImpl(MenuComponent menuComponentView) {
        addComponentsAndExpand(menuComponentView);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        Long cityId = (Long) VaadinSession.getCurrent().getAttribute(CityView.CITY_ID_ATTRIBUTE);
//        City city = cinemaService.getCityByName(cityId);
//        if (city != null) {
//            addComponentsAndExpand(createCityPanel(city));
//        } else {
//            Notification.show("City is not selected");
//        }
    }

    private Component createCityPanel(City city, UserRole role) {
        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Label label = new Label("Cinemas in " + city.getName());
        horizontalLayout.addComponentsAndExpand(label);
        switch (role) {
            case ADMINISTRATOR: {
                horizontalLayout.addComponentsAndExpand(new Button("Delete City", (Button.ClickListener) event -> deleteCity(city)));
            }
            break;
        }
        layout.addComponentsAndExpand(horizontalLayout);

        fillLayoutByCinemas(layout, city);

        switch (role) {
            case ADMINISTRATOR: {
                layout.addComponentsAndExpand(createAdminCinemaAddingPanel(city));
            }
            break;
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
        Button buttonCreateCity = new Button("Create Cinema", event -> createCinema(city));
        buttonCreateCity.setSizeFull();
        verticalLayout.addComponentsAndExpand(buttonCreateCity);
        return panel;
    }

    private void createCinema(City city) {
//        try {
//            cinemaService.createCinema(city, textFieldCinemaName.getValue(), textFieldCinemaStreet.getValue());
//            Page.getCurrent().reload();
//        } catch (IllegalArgumentException ex) {
//            Notification.show(ex.getMessage(), Notification.Type.WARNING_MESSAGE);
//        } catch (Exception ex) {
//            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
//        }
    }

    private void fillLayoutByCinemas(VerticalLayout layout, City city) {
//        for (Cinema cinema : city.getCityCinemas()) {
//            Button button = new Button(cinema.getLogin() + ", " + cinema.getLocation(), (Button.ClickListener) clickEvent -> new PageNavigator().navigateToCinemaView(getUI(), cinema.getId()));
//            button.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//            layout.addComponentsAndExpand(button);
//        }
    }

    private void deleteCity(City city) {
//        cinemaService.deleteCity(city);
//        Page.getCurrent().setUriFragment("!" + VaadinUI.NAVIGATION_MENU_VIEW);
    }

    @Override
    public void showWarning(String message) {
        Notification.show(message, Notification.Type.WARNING_MESSAGE);
    }

    @Override
    public void showError(String message) {
        Notification.show(message, Notification.Type.ERROR_MESSAGE);
    }

    @Override
    public void reload() {
        Page.getCurrent().reload();
    }
}
