package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.model.persistence.cinema.Comment;
import com.rustedbrain.study.course.model.persistence.cinema.Movie;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UIScope
@SpringView(name = VaadinUI.MOVIE_VIEW)
public class MovieViewImpl extends VerticalLayout implements MovieView {

    public static final String MOVIE_ATTRIBUTE = "movie";
    private List<MovieView.Listener> listeners = new ArrayList<>();
    private Panel moviePanel;
    private Panel commentsPanel;

    @Autowired
    public MovieViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
        addComponentsAndExpand(new Panel(getMoviePanel()));
        addComponentsAndExpand(new Panel(getCommentsPanel()));
    }

    private Panel getMoviePanel() {
        if (moviePanel == null) {
            moviePanel = new Panel();
        }
        return moviePanel;
    }

    private Panel getCommentsPanel() {
        if (commentsPanel == null) {
            commentsPanel = new Panel();
        }
        return commentsPanel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        listeners.forEach(listener -> listener.entered(event));
    }

    @Override
    public void showMovieInfo(Movie movie, boolean authorized) {
        moviePanel.setContent(getMovieInfoLayout(movie));
        commentsPanel.setContent(getMovieCommentsLayout(movie.getComments(), authorized));
    }

    private Component getMovieCommentsLayout(Set<Comment> commentsSet, boolean authorized) {
        VerticalLayout layout = new VerticalLayout();

        List<Comment> comments = commentsSet.stream().sorted(Comparator.comparing(Comment::getRegistrationDate)).collect(Collectors.toList());

        for (Comment comment : comments) {
            HorizontalLayout commentLayout = new HorizontalLayout();
            Button buttonProfile = new Button(comment.getUser().getLogin(), (Button.ClickListener) event -> listeners.forEach(listener -> listener.buttonProfileClicked(comment.getUser().getId())));
            buttonProfile.setWidth(200, Unit.PIXELS);
            commentLayout.addComponent(buttonProfile);

            VerticalLayout messageLayout = new VerticalLayout();
            messageLayout.addComponent(new Label(comment.getRegistrationDate().toString()));
            messageLayout.addComponent(new Label(comment.getMessage()));

            commentLayout.addComponent(messageLayout);
            layout.addComponent(new Panel(commentLayout));
        }

        if (authorized) {
            TextArea textArea = new TextArea();
            textArea.setSizeFull();
            Button buttonCreateMessage = new Button("Create message", (Button.ClickListener) event -> listeners.forEach(listener -> listener.buttonCreateMessageClicked(textArea.getValue())));
            layout.addComponents(textArea, buttonCreateMessage);
        }


        return layout;
    }

    private Layout getMovieInfoLayout(Movie movie) {
        GridLayout movieInfoPosterLayout = new GridLayout();
        movieInfoPosterLayout.setMargin(false);
        movieInfoPosterLayout.setColumns(2);
        movieInfoPosterLayout.setSizeFull();

        VerticalLayout posterLayout = new VerticalLayout();
        posterLayout.setWidth(375, Unit.PIXELS);
        Image image = new Image("", new FileResource(new File(movie.getPosterPath())));
        image.setWidth(365, Unit.PIXELS);
        image.setHeight(490, Unit.PIXELS);
        posterLayout.addComponent(image);
        movieInfoPosterLayout.addComponent(posterLayout, 0, 0);

        VerticalLayout infoLayout = new VerticalLayout();
        Label nameLabel = new Label("Name: " + movie.getLocalizedName());
        nameLabel.setSizeFull();
        infoLayout.addComponent(nameLabel);
        Label originalNameLabel = new Label("Original name: " + movie.getOriginalName());
        originalNameLabel.setSizeFull();
        infoLayout.addComponent(originalNameLabel);
        Label countryLabel = new Label("Country: " + movie.getCountry());
        countryLabel.setSizeFull();
        infoLayout.addComponent(countryLabel);
        Label releaseDateLabel = new Label("Year: " + movie.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).getYear());
        releaseDateLabel.setSizeFull();
        infoLayout.addComponent(releaseDateLabel);
        Label descriptionLabel = new Label(movie.getDescription());
        descriptionLabel.setSizeFull();
        infoLayout.addComponent(descriptionLabel);
        movieInfoPosterLayout.addComponent(infoLayout, 1, 0);

        movieInfoPosterLayout.setColumnExpandRatio(1, 2);
        return movieInfoPosterLayout;
    }

    @Override
    @Autowired
    public void addCinemaViewListener(MovieView.Listener listener) {
        listener.setView(this);
        this.listeners.add(listener);
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
}