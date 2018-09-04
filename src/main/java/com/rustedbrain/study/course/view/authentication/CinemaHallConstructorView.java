package com.rustedbrain.study.course.view.authentication;

import com.vaadin.navigator.View;
import org.springframework.beans.factory.annotation.Autowired;

public interface CinemaHallConstructorView extends View {
    @Autowired
    void addListener(ViewListener viewListener);

    interface ViewListener {
        void setCinemaHallConstructorView(CinemaHallConstructorView components);

        void addButtonClicked(String rows, String seats);
    }
}
