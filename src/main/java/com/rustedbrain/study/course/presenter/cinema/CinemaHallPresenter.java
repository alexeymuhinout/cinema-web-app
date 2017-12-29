package com.rustedbrain.study.course.presenter.cinema;

import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.cinema.CinemaHallView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class CinemaHallPresenter implements CinemaHallView.CinemaHallViewListener {


    private CinemaHallView cinemaHallView;
    private CinemaService cinemaService;

    @Autowired
    public CinemaHallPresenter(CinemaHallView cinemaHallView, CinemaService cinemaService) {
        this.cinemaHallView = cinemaHallView;
        this.cinemaService = cinemaService;

        cinemaHallView.addListener(this);
    }
}
