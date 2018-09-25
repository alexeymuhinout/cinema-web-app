package com.rustedbrain.study.course.view.authentication;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.rustedbrain.study.course.model.persistence.cinema.Actor;
import com.rustedbrain.study.course.model.persistence.cinema.Genre;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView(name = VaadinUI.MOVIE_EDIT_VIEW)
public class MovieAdvancedEditViewImpl extends VerticalLayout implements MovieAdvancedEditView {

	private static final long serialVersionUID = -3386283372124579119L;

	private Movie movie;
	private Movie editedMovie;
	private TabSheet tabSheet;
	private Panel movieEditPanel;
	private List<MovieAdvancedEditView.ViewListener> viewListeners = new ArrayList<>();

	private Panel movieInfoPanel;

	private List<Actor> actors;

	private List<Genre> genres;

	@Autowired
	public MovieAdvancedEditViewImpl(AuthenticationService authenticationService) {
		addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
		tabSheet = new TabSheet();
		tabSheet.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		addComponentsAndExpand(tabSheet);
	}

	@Override
	@Autowired
	public void addListener(ViewListener viewListener) {
		viewListener.setMovieEditView(this);
		viewListeners.add(viewListener);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		viewListeners.forEach(viewListener -> viewListener.entered(event));
	}

	@Override
	public void showWarning(String message) {
		Notification.show(message, Notification.Type.WARNING_MESSAGE);
	}

	@Override
	public void showError(String message) {
		Notification.show(message, Notification.Type.ERROR_MESSAGE);
	}

	private void showNotification(String message) {
		Notification notification = new Notification(message, Notification.Type.HUMANIZED_MESSAGE);
		notification.show(Page.getCurrent());
	}

	@Override
	public void reload() {
		Page.getCurrent().reload();
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		closeTab();
		event.navigate();
	}

	private void closeTab() {
		if ( this.tabSheet != null ) {
			this.tabSheet.removeAllComponents();
		}

	}

	private Panel getMovieEditPanel() {
		if ( movieEditPanel == null ) {
			movieEditPanel = new Panel();
		}
		return movieEditPanel;
	}

	private Panel getMovieInfoPanel() {
		if ( movieInfoPanel == null ) {
			movieInfoPanel = new Panel();
		}
		return movieInfoPanel;
	}

	@Override
	public void setMovieInformation(Movie movie, List<Actor> actors, List<Genre> genres) {
		this.movie = movie;
		this.actors = actors;
		this.genres = genres;
		GridLayout movieInfoEditLayout = new GridLayout();
		movieInfoEditLayout.setColumns(2);
		movieInfoEditLayout.setSizeFull();
		movieInfoEditLayout.setMargin(true);

		VerticalLayout infoLayout = getMovieInfoLayout();
		Panel movieInfoPanel = getMovieInfoPanel();
		movieInfoPanel.setContent(infoLayout);
		movieInfoEditLayout.addComponent(movieInfoPanel, 0, 0);

		VerticalLayout editLayout = getMovieEditLayout();
		movieInfoEditLayout.addComponent(editLayout, 1, 0);

		movieInfoEditLayout.setColumnExpandRatio(0, 1);
		movieInfoEditLayout.setColumnExpandRatio(1, 2);
		tabSheet.addTab(movieInfoEditLayout, "Movie edit");

	}

	private VerticalLayout getMovieInfoLayout() {
		VerticalLayout infoLayout = new VerticalLayout();
		Image image = new Image("<h1><b>" + movie.getLocalizedName() + "</b></h1>",
				new FileResource(new File(movie.getPosterPath())));
		image.setCaptionAsHtml(true);
		image.setAlternateText("POSTER");
		image.setWidth(150, Unit.PIXELS);
		image.setHeight(200, Unit.PIXELS);
		infoLayout.addComponent(image);
		Label originalNameLabel = new Label("Original title: " + movie.getOriginalName());
		originalNameLabel.setSizeFull();
		infoLayout.addComponent(originalNameLabel);
		Label countryLabel = new Label("Country: " + movie.getCountry());
		countryLabel.setSizeFull();
		infoLayout.addComponent(countryLabel);
		Label releaseDateLabel =
				new Label("Year: " + movie.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).getYear());
		releaseDateLabel.setSizeFull();
		infoLayout.addComponent(releaseDateLabel);
		Label minAge = new Label("Age: " + movie.getMinAge() + "+");
		minAge.setSizeFull();
		infoLayout.addComponent(minAge);
		Label producer = new Label("Producer: " + String.valueOf(movie.getProducer()));
		producer.setSizeFull();
		infoLayout.addComponent(producer);
		List<String> genresName = new ArrayList<>();
		movie.getGenres().forEach(genre -> genresName.add(genre.getName()));
		Label genres = new Label("Genres: " + genresName.toString());
		genres.setSizeFull();
		infoLayout.addComponent(genres);
		Label time = new Label("Time: " + String.valueOf(movie.getTimeMinutes()));
		time.setSizeFull();
		infoLayout.addComponent(time);
		List<String> actorsName = new ArrayList<>();
		movie.getActors().forEach(actor -> actorsName.add(actor.getName() + " " + actor.getSurname()));
		Label actors = new Label("Starring: " + actorsName.toString());
		actors.setSizeFull();
		infoLayout.addComponent(actors);
		Label trailerURL = new Label("Trailer URL: " + movie.getTrailerURL());
		trailerURL.setSizeFull();
		infoLayout.addComponent(trailerURL);
		Label descriptionLabel = new Label(movie.getDescription());
		descriptionLabel.setSizeFull();
		infoLayout.addComponent(descriptionLabel);
		return infoLayout;
	}

	private VerticalLayout getMovieEditLayout() {
		editedMovie = new Movie();
		VerticalLayout editLayout = new VerticalLayout();
		Binder<Movie> movieBinder = new Binder<Movie>();
		movieBinder.setBean(editedMovie);

		TextField localizedNameTextField = getLocalizeNameTextField(movieBinder);
		TextField minAgeTextField = getMinAgeTextField(movieBinder);
		TextField originalNameTextField = getOriginalNameTextField(movieBinder);
		TextField producerTextField = getProducerTextField(movieBinder);
		DateTimeField releaseDateTextField = getReleaseDateTextField(movieBinder);
		VerticalLayout genresInfoEditLayout = getGenresInfoEditLayout();
		TextField timeTextField = getTimeTextField(movieBinder);
		TextField countryTextField = getCountryTextField(movieBinder);
		VerticalLayout actorsInfoEditLayout = getActorsInfoEditLayout();
		TextArea descriptionTextArea = getDescriptionTextArea(movieBinder);

		TextField posterPathTextField = new TextField("Poster path");
		movieBinder.forField(posterPathTextField).withValidator(str -> str.length() > 0, "Poster path can't be emty")
				.bind(Movie::getPosterPath, Movie::setPosterPath);
		posterPathTextField.setValue(movie.getPosterPath());
		posterPathTextField.setSizeFull();

		TextField trailerURLTextField = getTrailerURLTextField(movieBinder);

		editLayout.addComponents(localizedNameTextField, minAgeTextField, originalNameTextField, producerTextField,
				releaseDateTextField, genresInfoEditLayout, timeTextField, countryTextField, actorsInfoEditLayout,
				descriptionTextArea, posterPathTextField, trailerURLTextField);

		Button saveButton = new Button("Save");
		saveButton.addStyleName("friendly");
		saveButton.addClickListener(clickEvent -> UI.getCurrent().addWindow(getSaveMovieConformationWindow()));
		editLayout.addComponent(saveButton);
		editLayout.setComponentAlignment(saveButton, Alignment.BOTTOM_RIGHT);
		return editLayout;
	}

	private VerticalLayout getGenresInfoEditLayout() {
		editedMovie.setGenres(movie.getGenres());
		VerticalLayout genresInfoEditLayout = new VerticalLayout();
		genresInfoEditLayout.setMargin(false);
		genresInfoEditLayout.setSpacing(false);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(false);

		TextField genresTextField = new TextField();
		genresTextField.setEnabled(false);
		genresTextField.setSizeFull();
		List<String> genresName = new ArrayList<>();
		movie.getGenres().forEach(genre -> genresName.add(genre.getName()));
		genresTextField.setValue(genresName.toString());

		Button editGenresButton = new Button("Edit");
		editGenresButton.addClickListener(clickEvent -> {
			editedMovie.setGenres(getNewGenresSet());
			List<String> newGenresName = new ArrayList<>();
			editedMovie.getGenres().forEach(genre -> newGenresName.add(genre.getName()));
			genresTextField.setValue(newGenresName.toString());
		});

		horizontalLayout.addComponents(genresTextField, editGenresButton);
		horizontalLayout.setExpandRatio(genresTextField, 1);
		horizontalLayout.setExpandRatio(editGenresButton, 0);

		Label genresLabel = new Label("Genres");

		genresInfoEditLayout.addComponentsAndExpand(genresLabel, horizontalLayout);
		return genresInfoEditLayout;
	}

	private Set<Genre> getNewGenresSet() {
		Window genresWindow = new Window("Genres");
		genresWindow.setWidth("600px");
		genresWindow.setModal(true);
		genresWindow.setResizable(true);
		genresWindow.setDraggable(false);

		Grid<Genre> genresGrid = new Grid<>();
		genresGrid.setSizeFull();
		genresGrid.setSelectionMode(SelectionMode.MULTI);
		genresGrid.setItems(genres);
		genresGrid.addColumn(Genre::getName).setCaption("Name");
		movie.getGenres().forEach(genre -> genresGrid.select(genre));

		Button buttonSave = new Button("Save");
		Set<Genre> genres = new HashSet<>();
		buttonSave.addClickListener(clickEvent -> {
			genres.addAll(genresGrid.asMultiSelect().getSelectedItems());
			genresWindow.close();
		});

		genresWindow.setContent(new VerticalLayout(genresGrid, buttonSave));
		UI.getCurrent().addWindow(genresWindow);
		return genres;
	}

	private VerticalLayout getActorsInfoEditLayout() {
		editedMovie.setActors(movie.getActors());
		VerticalLayout actorsInfoEditLayout = new VerticalLayout();
		actorsInfoEditLayout.setMargin(false);
		actorsInfoEditLayout.setSpacing(false);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(false);

		TextField actorsTextField = new TextField();
		actorsTextField.setEnabled(false);
		actorsTextField.setSizeFull();
		List<String> actorsName = new ArrayList<>();
		movie.getActors().forEach(actor -> actorsName.add(actor.getName() + " " + actor.getSurname()));
		actorsTextField.setValue(actorsName.toString());

		Button editActorsButton = new Button("Edit");
		editActorsButton.addClickListener(clickEvent -> {
			editedMovie.setActors(getNewActorsSet());
		});

		horizontalLayout.addComponents(actorsTextField, editActorsButton);
		horizontalLayout.setExpandRatio(actorsTextField, 1);
		horizontalLayout.setExpandRatio(editActorsButton, 0);

		Label actorsLabel = new Label("Starring");

		actorsInfoEditLayout.addComponentsAndExpand(actorsLabel, horizontalLayout);
		return actorsInfoEditLayout;
	}

	private Set<Actor> getNewActorsSet() {
		Window actorsWindow = new Window("Actors");
		actorsWindow.setWidth("600px");
		actorsWindow.setModal(true);
		actorsWindow.setResizable(true);
		actorsWindow.setDraggable(false);

		Grid<Actor> actorGrid = new Grid<>();
		actorGrid.setSizeFull();
		actorGrid.setSelectionMode(SelectionMode.MULTI);
		actorGrid.setItems(actors);
		actorGrid.addColumn(Actor::getName).setCaption("Name");
		actorGrid.addColumn(Actor::getSurname).setCaption("Surname");
		movie.getActors().forEach(actor -> actorGrid.select(actor));

		Button buttonSave = new Button("Save");
		Set<Actor> actors = new HashSet<>();
		buttonSave.addClickListener(clickEvent -> {
			actors.addAll(actorGrid.asMultiSelect().getSelectedItems());
			actorsWindow.close();
		});

		actorsWindow.setContent(new VerticalLayout(actorGrid, buttonSave));
		UI.getCurrent().addWindow(actorsWindow);
		return actors;
	}

	private TextField getTrailerURLTextField(Binder<Movie> movieBinder) {
		TextField trailerURLTextField = new TextField("Trailer URL");
		movieBinder.forField(trailerURLTextField).withValidator(str -> str.length() > 0, "Trailer URL can't be emty")
				.bind(Movie::getTrailerURL, Movie::setTrailerURL);
		trailerURLTextField.setValue(movie.getTrailerURL());
		trailerURLTextField.setSizeFull();
		return trailerURLTextField;
	}

	private TextArea getDescriptionTextArea(Binder<Movie> movieBinder) {
		TextArea descriptionTextArea = new TextArea("Description");
		movieBinder.forField(descriptionTextArea).withValidator(str -> str.length() > 0, "Description can't be emty")
				.bind(Movie::getDescription, Movie::setDescription);
		descriptionTextArea.setSizeFull();
		descriptionTextArea.setValue(movie.getDescription());
		return descriptionTextArea;
	}

	private TextField getCountryTextField(Binder<Movie> movieBinder) {
		TextField countryTextField = new TextField("Country");
		movieBinder.forField(countryTextField).bind(Movie::getCountry, Movie::setCountry);
		countryTextField.setSizeFull();
		countryTextField.setValue(String.valueOf(movie.getCountry()));
		return countryTextField;
	}

	private TextField getTimeTextField(Binder<Movie> movieBinder) {
		TextField timeTextField = new TextField("Time");
		movieBinder.forField(timeTextField).withConverter(new StringToIntegerConverter("Must be Integer"))
				.bind(Movie::getTimeMinutes, Movie::setTimeMinutes);
		timeTextField.setSizeFull();
		timeTextField.setValue(String.valueOf(movie.getTimeMinutes()));
		return timeTextField;
	}

	private DateTimeField getReleaseDateTextField(Binder<Movie> movieBinder) {
		DateTimeField releaseDateTextField = new DateTimeField("Release date");
		movieBinder.forField(releaseDateTextField)
				.withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
				.bind(Movie::getReleaseDate, Movie::setReleaseDate);
		releaseDateTextField.setSizeFull();
		releaseDateTextField
				.setValue(LocalDateTime.ofInstant(movie.getReleaseDate().toInstant(), ZoneId.systemDefault()));
		return releaseDateTextField;
	}

	private TextField getProducerTextField(Binder<Movie> movieBinder) {
		TextField producerTextField = new TextField("Producer");
		movieBinder.forField(producerTextField).bind(Movie::getProducer, Movie::setProducer);
		producerTextField.setSizeFull();
		producerTextField.setValue(String.valueOf(movie.getProducer()));
		return producerTextField;
	}

	private TextField getOriginalNameTextField(Binder<Movie> movieBinder) {
		TextField originalNameTextField = new TextField("Original title");
		movieBinder.forField(originalNameTextField)
				.withValidator(str -> str.length() > 0, "Movie original name can't be emty")
				.bind(Movie::getOriginalName, Movie::setOriginalName);
		originalNameTextField.setValue(movie.getOriginalName());
		originalNameTextField.setSizeFull();
		return originalNameTextField;
	}

	private TextField getMinAgeTextField(Binder<Movie> movieBinder) {
		TextField minAgeTextField = new TextField("Age");
		movieBinder.forField(minAgeTextField).withValidator(str -> str.length() < 4, "Must be less than 4 chars")
				.withConverter(new StringToIntegerConverter("Must be Integer"))
				.bind(Movie::getMinAge, Movie::setMinAge);
		minAgeTextField.setValue(String.valueOf(movie.getMinAge()));
		minAgeTextField.setSizeFull();
		return minAgeTextField;
	}

	private TextField getLocalizeNameTextField(Binder<Movie> movieBinder) {
		TextField localizedNameTextField = new TextField("Name");
		movieBinder.forField(localizedNameTextField).withValidator(str -> str.length() > 0, "Movie name can't be emty")
				.bind(Movie::getLocalizedName, Movie::setLocalizedName);
		localizedNameTextField.setSizeFull();
		localizedNameTextField.setValue(movie.getLocalizedName());
		return localizedNameTextField;
	}

	private Window getSaveMovieConformationWindow() {
		Window saveMovieConformationWindow = new Window();
		saveMovieConformationWindow.setHeight("100px");
		saveMovieConformationWindow.setWidth("180px");
		saveMovieConformationWindow.setModal(true);
		saveMovieConformationWindow.setResizable(false);
		saveMovieConformationWindow.setDraggable(false);

		Label saveLabel = new Label("Save Movie?");
		saveLabel.setSizeFull();
		Button saveButton = new Button("Save", (Button.ClickListener) event -> {
			viewListeners.forEach(listener -> listener.buttonSaveMovieButtonClicked(editedMovie));
			saveMovieConformationWindow.close();
			showNotification("Movie saved");
		});
		saveButton.addStyleName("friendly");

		VerticalLayout saveButtonLayout = new VerticalLayout();
		saveButtonLayout.addComponentsAndExpand(saveLabel, saveButton);
		saveMovieConformationWindow.setContent(saveButtonLayout);

		return saveMovieConformationWindow;
	}
}
