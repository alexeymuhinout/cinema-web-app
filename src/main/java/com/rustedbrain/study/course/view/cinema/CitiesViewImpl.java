package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.presenter.cinema.CitiesViewPresenter;
import com.rustedbrain.study.course.view.util.NotificationUtil;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CitiesViewImpl extends NavigableView implements CitiesView {

    private Collection<CitiesView.CitiesViewListener> citiesViewListeners = new ArrayList<>();

    @Autowired
    public void initPresenter(CitiesViewPresenter presenter) {
        presenter.setView(this);
        presenter.bind();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        NotificationUtil.showAvailableMessage();
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

    @Override
    public void fillCitiesPanel(List<City> cities) {

    }

    @Override
    public void addCitiesViewListener(CitiesViewListener listener) {
        citiesViewListeners.add(listener);
    }
}
