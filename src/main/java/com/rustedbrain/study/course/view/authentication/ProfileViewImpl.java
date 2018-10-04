package com.rustedbrain.study.course.view.authentication;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.rustedbrain.study.course.model.dto.UserRole;
import com.rustedbrain.study.course.model.persistence.authorization.User;
import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreeningEvent;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.authentication.layout.AdminProfileEditTab;
import com.rustedbrain.study.course.view.authentication.layout.AdminProfileInfoLayout;
import com.rustedbrain.study.course.view.authentication.layout.AdministartionMoviePanel;
import com.rustedbrain.study.course.view.authentication.layout.AdministrationCinemaHallPanel;
import com.rustedbrain.study.course.view.authentication.layout.AdministrationCinemaPanel;
import com.rustedbrain.study.course.view.authentication.layout.AdministrationCityPanel;
import com.rustedbrain.study.course.view.authentication.layout.AdministrationFilmScreeningPanel;
import com.rustedbrain.study.course.view.authentication.layout.AdministrationStatisticPanel;
import com.rustedbrain.study.course.view.authentication.layout.ProfileEditTab;
import com.rustedbrain.study.course.view.authentication.layout.ProfileInfoLayout;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView(name = VaadinUI.PROFILE_VIEW)
public class ProfileViewImpl extends VerticalLayout implements ProfileView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1036618039435390691L;

	private List<ProfileView.ViewListener> listeners = new ArrayList<>();

	private TabSheet tabSheet;
	private ProfileInfoLayout profileInfoTab;
	private ProfileEditTab profileEditTab;
	private TabSheet adminLayout;
	private Panel statisticsLayout;
	private Window userBlockWindow;
	private Window filmScreeningEventsWindow;
	private AdministrationCityPanel administrationCityPanel;
	private AdministrationCinemaPanel administrationCinemaPanel;
	private AdministrationCinemaHallPanel administrationCinemaHallPanel;
	private AdministartionMoviePanel administrationMoviePanel;

	private AdministrationFilmScreeningPanel administrationFilmScreeningPanel;

	@Autowired
	public ProfileViewImpl(AuthenticationService authenticationService) {
		addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
		tabSheet = new TabSheet();
		tabSheet.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		addComponentsAndExpand(tabSheet);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		listeners.forEach(viewListener -> viewListener.entered(event));
	}

	@Override
	@Autowired
	public void addListener(ProfileView.ViewListener listener) {
		listener.setView(this);
		listeners.add(listener);
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
	public void reload() {
		Page.getCurrent().reload();
	}

	@Override
	public void addProfileInfoTab(User user) {
		if ( profileInfoTab != null ) {
			Component oldProfileInfoLayout = this.profileInfoTab;
			this.profileInfoTab = new ProfileInfoLayout(listeners, user);
			tabSheet.replaceComponent(oldProfileInfoLayout, profileInfoTab);
		} else {
			this.profileInfoTab = new ProfileInfoLayout(listeners, user);
			tabSheet.addTab(profileInfoTab, "Info");
		}
	}

	@Override
	public void addProfileAdminEditTab(User user, List<User> users, List<City> cities) {
		if ( profileEditTab != null ) {
			Component oldProfileEditLayout = this.profileEditTab;
			this.profileEditTab = new AdminProfileEditTab(listeners, user, users, cities);
			tabSheet.replaceComponent(oldProfileEditLayout, profileEditTab);
		} else {
			this.profileEditTab = new AdminProfileEditTab(listeners, user, users, cities);
			tabSheet.addTab(profileEditTab, "Edit");
		}
	}

	@Override
	public void closeUserBlockWindow() {
		if ( this.userBlockWindow != null ) {
			this.userBlockWindow.close();
		}
	}

	@Override
	public void closeFilmScreeningEventsWindow() {
		if ( this.filmScreeningEventsWindow != null ) {
			this.filmScreeningEventsWindow.close();
		}
	}

	@Override
	public void addAdminProfileInfoTab(User authenticUser, List<User> users) {
		if ( profileInfoTab != null ) {
			Component oldProfileInfoLayout = this.profileInfoTab;
			this.profileInfoTab = new AdminProfileInfoLayout(listeners, authenticUser, users);
			tabSheet.replaceComponent(oldProfileInfoLayout, profileInfoTab);
		} else {
			this.profileInfoTab = new AdminProfileInfoLayout(listeners, authenticUser, users);
			tabSheet.addTab(profileInfoTab, "Info");
		}
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		closeUserBlockWindow();
		event.navigate();
	}

	@Override
	public void showUserBlockWindow(long id, String login, UserRole userRole) {
		closeUserBlockWindow();
		userBlockWindow = new Window("Block User");
		userBlockWindow.addCloseListener((Window.CloseListener) e -> userBlockWindow = null);
		userBlockWindow.setSizeUndefined();
		userBlockWindow.setModal(true);
		userBlockWindow.setResizable(false);
		userBlockWindow.setDraggable(false);

		Label warnLabel = new Label("If you want to block user \"" + login + "\", please specify date and description");
		DateTimeField blockDateTimeField = new DateTimeField("Block date", LocalDateTime.now());
		TextArea blockDescrTextField = new TextArea("Reason of blocking");
		blockDescrTextField.setSizeFull();
		Button buttonBlock =
				new Button("Block", (Button.ClickListener) event -> listeners.forEach(viewListener -> viewListener
						.buttonBlockSubmitClicked(id, blockDateTimeField.getValue(), blockDescrTextField.getValue())));
		userBlockWindow.setContent(new VerticalLayout(warnLabel, blockDateTimeField, blockDescrTextField, buttonBlock));

		UI.getCurrent().addWindow(userBlockWindow);
	}

	@Override
	public void addAdministrationTab(User currUser, List<City> cities, List<Movie> movies) {
		if ( adminLayout != null ) {
			TabSheet oldAdminLayout = this.adminLayout;
			this.adminLayout = createAdminTab(cities, movies);
			tabSheet.replaceComponent(oldAdminLayout, this.adminLayout);
		} else {
			this.adminLayout = createAdminTab(cities, movies);
			tabSheet.addTab(adminLayout, "Administration");
		}
	}

	private TabSheet createAdminTab(List<City> cities, List<Movie> movies) {
		TabSheet tabSheet = new TabSheet();
		administrationCityPanel = new AdministrationCityPanel(listeners, cities);
		administrationCinemaPanel = new AdministrationCinemaPanel(listeners, cities);
		administrationCinemaHallPanel = new AdministrationCinemaHallPanel(listeners, cities);
		List<Cinema> cinemas = new ArrayList<>();
		cities.forEach(city -> cinemas.addAll(city.getCinemas()));
		administrationMoviePanel = new AdministartionMoviePanel(listeners, movies);
		administrationFilmScreeningPanel = new AdministrationFilmScreeningPanel(listeners, cities, movies);
		tabSheet.addTab(administrationCityPanel, "City");
		tabSheet.addTab(administrationCinemaPanel, "Cinema");
		tabSheet.addTab(administrationCinemaHallPanel, "Cinema Hall");
		tabSheet.addTab(administrationMoviePanel, "Movie");
		tabSheet.addTab(administrationFilmScreeningPanel, "Film Screening");
		return tabSheet;
	}

	@Override
	public void addStatisticsTab(List<City> cities) {
		if ( statisticsLayout != null ) {
			Panel oldStatisticsLayout = this.statisticsLayout;
			this.statisticsLayout = createStatisticsTab(cities);
			tabSheet.replaceComponent(oldStatisticsLayout, this.statisticsLayout);
		} else {
			this.statisticsLayout = createStatisticsTab(cities);
			tabSheet.addTab(statisticsLayout, "Statistics");
		}
	}

	@Override
	public Panel createStatisticsTab(List<City> cities) { 
		return new AdministrationStatisticPanel(listeners,cities);
	}

	@Override
	public void setAdminEditUserSelected(User currUser) {
		profileEditTab.setSelectedUser(currUser);
	}

	@Override
	public void setAdminInfoUserSelected(User selectedUser) {
		profileInfoTab.setSelectedUser(selectedUser);
	}

	@Override
	public void addProfileEditTab(User currUser, List<City> cities) {
		if ( profileEditTab != null ) {
			Component oldProfileEditLayout = this.profileEditTab;
			this.profileEditTab = new ProfileEditTab(listeners, currUser, cities);
			tabSheet.replaceComponent(oldProfileEditLayout, profileEditTab);
		} else {
			this.profileEditTab = new ProfileEditTab(listeners, currUser, cities);
			tabSheet.addTab(profileEditTab, "Edit");
		}
	}

	@Override
	public void showFilmScreeningEventsWindow(FilmScreening selectedFilmScreening) {
		closeFilmScreeningEventsWindow();
		filmScreeningEventsWindow = new Window("Film screening events");
		filmScreeningEventsWindow.addCloseListener((Window.CloseListener) e -> filmScreeningEventsWindow = null);
		filmScreeningEventsWindow.setWidth("800px");
		filmScreeningEventsWindow.setModal(true);
		filmScreeningEventsWindow.setDraggable(false);

		List<FilmScreeningEvent> filmScreeningEvents = selectedFilmScreening.getFilmScreeningEvents();

		Grid<FilmScreeningEvent> grid = new Grid<>();
		grid.setItems(filmScreeningEvents);
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.setSizeFull();

		grid.addColumn(FilmScreeningEvent::getDate).setCaption("Date");
		grid.addColumn(FilmScreeningEvent::getTime).setCaption("Time");
		grid.addColumn(filmScreeningEvent -> filmScreeningEvent.getCinemaHall().getCinema().getName())
				.setCaption("Cinema");
		grid.addColumn(filmScreeningEvent -> filmScreeningEvent.getCinemaHall().getName()).setCaption("Cinema hall");
		grid.addColumn(filmScreeningEvent -> "Delete", new ButtonRenderer<FilmScreeningEvent>(clickEvent -> {
			filmScreeningEvents.remove(clickEvent.getItem());
			grid.setItems(filmScreeningEvents);
			listeners.forEach(listener -> listener.getFilmScreeningEditPresenter()
					.buttonDeleteFilmScreeningEventClicked(clickEvent.getItem().getId()));
		}));

		VerticalLayout verticalLayout = new VerticalLayout();

		Button addFilmScreeningEvent = new Button("Add");
		addFilmScreeningEvent.addClickListener(e -> {
			verticalLayout.addComponent(addNewFilmScreeningEventButtonClick(selectedFilmScreening,grid, filmScreeningEvents));
		});

		verticalLayout.addComponents(grid, addFilmScreeningEvent);
		filmScreeningEventsWindow.setContent(verticalLayout);
		UI.getCurrent().addWindow(filmScreeningEventsWindow);
	}

	private PopupView addNewFilmScreeningEventButtonClick(FilmScreening filmScreening, Grid<FilmScreeningEvent> grid,
			List<FilmScreeningEvent> filmScreeningEvents) {
		VerticalLayout createPopupContent = new VerticalLayout();
		
		Set<CinemaHall> cinemaHalls = filmScreening.getCinema().getCinemaHalls();
		ComboBox<CinemaHall> cinemaHallComboBox = new ComboBox<>("Cinema hall");
		cinemaHallComboBox.setEmptySelectionAllowed(false);
		cinemaHallComboBox.setItems(cinemaHalls);
		cinemaHallComboBox.setItemCaptionGenerator(CinemaHall::getName);
		cinemaHallComboBox.setSelectedItem(cinemaHalls.iterator().next());
		cinemaHallComboBox.setSizeFull();

		DateTimeField filmScreeningEventDateTimeField = new DateTimeField("Date time");
		createPopupContent.addComponents(cinemaHallComboBox, filmScreeningEventDateTimeField);

		createPopupContent.addComponent(new Button("Add", (Button.ClickListener) event -> {
			listeners.forEach(listener -> {
				if ( cinemaHallComboBox.getSelectedItem().isPresent() ) {
					LocalDate dateEvent =
							filmScreeningEventDateTimeField.getValue().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalTime timeEvent =
							filmScreeningEventDateTimeField.getValue().atZone(ZoneId.systemDefault()).toLocalTime();
					ZonedDateTime zdtEnd = ZonedDateTime.ofInstant(filmScreening.getEndDate().toInstant(), ZoneId.systemDefault());
					ZonedDateTime zdtStart = ZonedDateTime.ofInstant(filmScreening.getStartDate().toInstant(), ZoneId.systemDefault());
					if ( filmScreeningEventDateTimeField.getValue().isBefore(zdtEnd.toLocalDateTime())
							&& filmScreeningEventDateTimeField.getValue().isAfter(zdtStart.toLocalDateTime()) ) {
						listener.getFilmScreeningEditPresenter().buttonAddNewFilmScreeningEventClicked(filmScreening,
								cinemaHallComboBox.getSelectedItem().get(), Date.valueOf(dateEvent),
								Time.valueOf(timeEvent));
						filmScreeningEvents.add(new FilmScreeningEvent(cinemaHallComboBox.getSelectedItem().get(),
								Date.valueOf(dateEvent), Time.valueOf(timeEvent)));
						grid.setItems(filmScreeningEvents);
					} else {
						Notification.show("Date and time must be after start date of film screening", "",
								Notification.Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Please select cinema hall", "", Notification.Type.ERROR_MESSAGE);
				}
			});
		}));

		PopupView popup = new PopupView(null, createPopupContent);
		popup.setSizeUndefined();
		popup.setPopupVisible(true);
		return popup;
	}
}