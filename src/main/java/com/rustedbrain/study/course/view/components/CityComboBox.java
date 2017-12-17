package com.rustedbrain.study.course.view.components;

import com.rustedbrain.study.course.model.cinema.City;
import com.vaadin.ui.ComboBox;

import java.util.Collection;
import java.util.List;

public class CityComboBox extends ComboBox<City> {

    public CityComboBox(List<City> cities) {
        super();
        this.setItemCaptionGenerator(City::getName);
        this.setEmptySelectionAllowed(false);

        this.setItems(cities);

        this.setNewItemHandler(inputString -> {
            City city = new City(inputString);
            cities.add(city);
            this.setItems(cities);
            this.setSelectedItem(city);
        });
    }

    public CityComboBox(List<City> cities, String caption) {
        super(caption);
        this.setItemCaptionGenerator(City::getName);
        this.setEmptySelectionAllowed(false);

        this.setItems(cities);

        this.setNewItemHandler(inputString -> {
            City city = new City(inputString);
            cities.add(city);
            this.setItems(cities);
            this.setSelectedItem(city);
        });
    }

    private CityComboBox() {
        super();
    }

    private CityComboBox(String caption) {
        super(caption);
    }

    private CityComboBox(String caption, Collection<City> options) {
        super(caption, options);
    }
}
