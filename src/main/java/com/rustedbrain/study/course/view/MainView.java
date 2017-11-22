package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.controller.service.CinemaService;
import com.rustedbrain.study.course.model.cinema.Cinema;
import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.view.components.Menu;
import com.vaadin.data.TreeData;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringView(name = VaadinUI.MAIN_VIEW)
public class MainView extends VerticalLayout implements View {

    @Autowired
    CinemaService cinemaService;

    public MainView() {
        setSizeFull();
        setSpacing(true);
        addComponent(createMenuPanel());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponentsAndExpand(createCityCinemasPanel());
        layout.addComponentsAndExpand(createNewsPanel());
        addComponent(layout);
    }

    private Component createNewsPanel() {
        Label label = new Label("Latest news");
        Panel panel = new Panel(label);
        panel.setSizeFull();
        return panel;
    }

    private Panel createMenuPanel() {
        FormLayout content = new FormLayout();
        content.addComponent(new Menu());
        content.setMargin(true);
        return new Panel(content);
    }

    private Panel createCityCinemasPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label label = new Label("Cities&Cinemas");

        TreeData<String> treeData = new TreeData<>();

        List<City> cities = cinemaService.getCities();

        treeData.addRootItems(cities.stream().map(City::getName));

        cities.forEach(x -> treeData.addItems(x.getName(), x.getCinemas().stream().map(Cinema::getName)));

        Tree<String> tree = new Tree<>();
        tree.setTreeData(treeData);

        verticalLayout.addComponent(label);
        verticalLayout.addComponent(tree);

        Panel panel = new Panel(verticalLayout);
        panel.setSizeFull();
        return panel;
    }


}