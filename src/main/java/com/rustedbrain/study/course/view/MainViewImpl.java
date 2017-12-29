package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.auth.LoginViewImpl;
import com.rustedbrain.study.course.view.cinema.NavigationView;
import com.rustedbrain.study.course.view.components.CityComboBox;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.data.TreeData;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringView(name = VaadinUI.MAIN_VIEW)
public class MainViewImpl extends NavigationView {

    private Pattern patternCinemaStreet = Pattern.compile(" \\(.+\\)");

    private CinemaService cinemaService;

    private TextField textFieldCityName;

    @Autowired
    public void setCinemaService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponentsAndExpand(createCityCinemasPanel());
        layout.addComponentsAndExpand(createNewsPanel());
        addComponentsAndExpand(layout);
        if (VaadinSession.getCurrent().getAttribute(VaadinUI.MESSAGE_ATTRIBUTE) != null) {
            Notification.show(String.valueOf(VaadinSession.getCurrent().getAttribute(VaadinUI.MESSAGE_ATTRIBUTE)), Notification.Type.HUMANIZED_MESSAGE);
            VaadinSession.getCurrent().setAttribute(VaadinUI.MESSAGE_ATTRIBUTE, null);
        }
    }

    private Component createNewsPanel() {
        Label label = new Label("Latest news");
        Panel panel = new Panel(label);
        panel.setSizeFull();
        return panel;
    }

    private Panel createCityCinemasPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();

        List<City> cities = cinemaService.getCities();

        ComboBox<City> cityComboBox = new CityComboBox(cities);
        cityComboBox.setSizeFull();
        HorizontalLayout layoutLabelAndFind = new HorizontalLayout();
        layoutLabelAndFind.addComponentsAndExpand(cityComboBox);

        Button button = new Button("Go", (Button.ClickListener) event -> new PageNavigator().navigateToCityView(getUI(), cityComboBox.getValue().getId()));
        button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        layoutLabelAndFind.addComponent(button);

        verticalLayout.addComponentsAndExpand(layoutLabelAndFind, createCityAndCinemasTree(cities));

        if (VaadinSession.getCurrent().getAttribute(LoginViewImpl.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            verticalLayout.addComponentsAndExpand(createAdminCityAddingPanel());
        }

        Panel panel = new Panel(verticalLayout);
        panel.setSizeFull();
        return panel;
    }

    private Component createCityAndCinemasTree(List<City> cities) {
        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(new Label("Cities&Cinemas"));

        TreeData<String> treeData = new TreeData<>();

        treeData.addRootItems(cities.stream().map(City::getName));

        cities.forEach(x -> treeData.addItems(x.getName(), x.getCinemas().stream().map(cinema -> cinema.getName() + " (" + cinema.getLocation() + ")")));

        Tree<String> tree = new Tree<>();
        tree.setTreeData(treeData);
        tree.addItemClickListener((Tree.ItemClickListener<String>) event -> {
            String name = event.getItem();
            City city = cinemaService.getCity(name);
            if (city != null) {
                new PageNavigator().navigateToCityView(getUI(), city.getId());
            } else {
                Matcher matcher = patternCinemaStreet.matcher(name);
                if (matcher.find()) {
                    name = matcher.replaceAll("").trim();
                }
                Cinema cinema = cinemaService.getCinema(name);
                new PageNavigator().navigateToCinemaView(getUI(), cinema.getId());
            }
        });

        layout.addComponent(tree);
        return layout;
    }

    private Component createAdminCityAddingPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        textFieldCityName = new TextField("Name");
        horizontalLayout.addComponentsAndExpand(textFieldCityName);

        verticalLayout.addComponentsAndExpand(horizontalLayout);
        Button buttonCreateCity = new Button("Create city", event -> createCity());
        buttonCreateCity.setSizeFull();
        verticalLayout.addComponentsAndExpand(buttonCreateCity);
        Panel panel = new Panel(verticalLayout);
        panel.setSizeFull();
        return panel;
    }


    private void createCity() {
        try {
            cinemaService.createCity(textFieldCityName.getValue());
            Page.getCurrent().reload();
        } catch (IllegalArgumentException ex) {
            Notification.show(ex.getMessage(), Notification.Type.WARNING_MESSAGE);
        } catch (Exception ex) {
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }
}