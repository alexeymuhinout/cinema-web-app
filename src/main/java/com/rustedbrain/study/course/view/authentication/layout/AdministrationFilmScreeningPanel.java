package com.rustedbrain.study.course.view.authentication.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.view.authentication.ProfileView;
import com.rustedbrain.study.course.view.authentication.ProfileView.ViewListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class AdministrationFilmScreeningPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6730582989061302558L;

	protected VerticalLayout layout = new VerticalLayout();
	private List<ProfileView.ViewListener> listeners;
	private Set<FilmScreening> filmScreenings = new HashSet<>();
	private List<Cinema> cinemas = new ArrayList<>();
	private Grid<FilmScreening> grid = new Grid<>();

	public AdministrationFilmScreeningPanel(List<ViewListener> listeners, List<Cinema> cinemas) {
		this.listeners = listeners;
		this.cinemas = cinemas;
		this.cinemas.forEach(cinema -> filmScreenings.addAll(cinema.getFilmScreenings()));
		this.layout.addComponent(new Panel(showFilmScreeningsSelectionPanel(filmScreenings)));
		setContent(this.layout);
	}

	private Layout showFilmScreeningsSelectionPanel(Set<FilmScreening> filmScreenings) {
		VerticalLayout mainLayout = new VerticalLayout();
		grid.setItems(filmScreenings);
		grid.addColumn(filmScreening -> filmScreening.getMovie().getLocalizedName()).setCaption("Movie name");
		grid.addColumn(filmScreening -> filmScreening.getCinema().getName()).setCaption("Cinema");
		
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.setSizeFull();
		return null;
	}

}
