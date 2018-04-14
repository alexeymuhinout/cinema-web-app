package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.view.ApplicationView;
import com.vaadin.navigator.ViewChangeListener;
import org.springframework.beans.factory.annotation.Autowired;

public interface MovieView extends ApplicationView {

    void showMovieInfo(Movie movie, boolean authorized);

    @Autowired
    void addCinemaViewListener(Listener listener);

    interface Listener {
        void entered(ViewChangeListener.ViewChangeEvent event);

        void setView(MovieView view);

        void buttonProfileClicked(long id);

        void buttonCreateMessageClicked(String textArea);
    }
}
