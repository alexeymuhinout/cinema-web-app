package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.components.CityComboBox;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.rustedbrain.study.course.view.util.NotificationUtil;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@UIScope
@SpringView(name = VaadinUI.MAIN_VIEW)
public class MainViewImpl extends VerticalLayout implements MainView {

    private Collection<MainViewListener> mainViewListeners = new ArrayList<>();
    private Panel mainViewPanel;

    @Autowired
    public MainViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponentsAndExpand(createMainViewPanel());
        layout.setSizeFull();
        addComponentsAndExpand(layout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        NotificationUtil.showAvailableMessage();
        mainViewListeners.forEach(MainViewListener::entered);
    }

    private Panel createMainViewPanel() {
        mainViewPanel = new Panel();
        mainViewPanel.setWidth(100, Unit.PERCENTAGE);
        mainViewPanel.setHeight(100, Unit.PERCENTAGE);
        return mainViewPanel;
    }

    @Override
    public void fillFilmScreeningsPanel(List<FilmScreening> screenings) {

    }

    @Override
    @Autowired
    public void addMainViewListener(MainViewListener mainViewListener) {
        mainViewListener.setView(this);
        this.mainViewListeners.add(mainViewListener);
    }

    @Override
    public void showCitySelectionPanel(List<City> cities) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final Label label = new Label("City was not identified, please select your city", ContentMode.TEXT);
        label.getWidth();
        layout.addComponent(label);
        CityComboBox cityComboBox = new CityComboBox(cities);
        cityComboBox.addSelectionListener((SingleSelectionListener<City>) singleSelectionEvent -> {
            Optional<City> cityOptional = singleSelectionEvent.getSelectedItem();
            cityOptional.ifPresent(city -> {
                mainViewListeners.forEach(x -> x.comboBoxCitySelectionValueSelected(city));
            });
        });
        layout.addComponent(cityComboBox);
        mainViewPanel.setContent(layout);
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
    public void reloadPage() {
        Page.getCurrent().reload();
    }
}