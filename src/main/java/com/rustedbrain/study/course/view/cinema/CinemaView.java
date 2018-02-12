package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.view.ApplicationView;
import com.vaadin.navigator.ViewChangeListener;
import org.springframework.beans.factory.annotation.Autowired;

public interface CinemaView extends ApplicationView {

    @Autowired
    void addCinemaViewListener(CinemaViewListener listener);

    void fillCinemaPanel(Cinema cinema);

    interface CinemaViewListener {

        void entered(ViewChangeListener.ViewChangeEvent event);

        void setView(CinemaView cinemaView);

        void buttonFilmViewTimeClicked(long id);
    }
}
