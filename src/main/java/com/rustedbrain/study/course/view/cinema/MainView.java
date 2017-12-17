package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.CityComboBox;
import com.rustedbrain.study.course.view.users.LoginView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.data.TreeData;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringView(name = VaadinUI.MAIN_VIEW)
public class MainView extends NavigationView {

    @Autowired
    CinemaService cinemaService;

    private TextField textFieldCityName;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponentsAndExpand(createCityCinemasPanel());
        layout.addComponentsAndExpand(createNewsPanel());
        addComponent(layout);
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
        Label label = new Label("Cities&Cinemas");

        List<City> cities = cinemaService.getCities();

        ComboBox<City> cityComboBox = new CityComboBox(cities);

        Button findCityButton = new Button("Go");
        findCityButton.addClickListener((Button.ClickListener) event -> new PageNavigator().navigateToCityView(getUI(), cityComboBox.getValue().getName()));

        HorizontalLayout horizontalLayoutLabelAndFind = new HorizontalLayout();
        horizontalLayoutLabelAndFind.addComponentsAndExpand(label);
        horizontalLayoutLabelAndFind.addComponentsAndExpand(cityComboBox);
        horizontalLayoutLabelAndFind.addComponent(findCityButton);

        TreeData<String> treeData = new TreeData<>();

        treeData.addRootItems(cities.stream().map(City::getName));

        cities.forEach(x -> treeData.addItems(x.getName(), x.getCinemas().stream().map(cinema -> cinema.getName() + " (" + cinema.getLocation() + ")")));

        Tree<String> tree = new Tree<>();
        tree.setTreeData(treeData);
        tree.addItemClickListener((Tree.ItemClickListener<String>) event -> {
            String name = event.getItem();
            City city = cinemaService.getCity(name);
            new PageNavigator().navigateToCityView(getUI(), city.getName());
            Cinema cinema = cinemaService.getCinema(name);
            new PageNavigator().navigateToCinemaView(getUI(), cinema);
        });

        verticalLayout.addComponent(horizontalLayoutLabelAndFind);
        verticalLayout.addComponent(tree);

        if (VaadinSession.getCurrent().getAttribute(LoginView.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            textFieldCityName = new TextField();
            Button buttonCreateCity = new Button("Create city", event -> createCity());
            horizontalLayout.addComponentsAndExpand(textFieldCityName);
            horizontalLayout.addComponentsAndExpand(buttonCreateCity);
            verticalLayout.addComponent(horizontalLayout);
        }

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