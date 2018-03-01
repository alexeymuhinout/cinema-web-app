package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.model.persistence.cinema.FilmScreening;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.components.CityComboBox;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@UIScope
@SpringView(name = VaadinUI.MAIN_VIEW)
public class MainViewImpl extends VerticalLayout implements MainView {

    private Collection<MainViewListener> mainViewListeners = new ArrayList<>();
    private Panel menuPanel;
    private Panel mainViewPanel;
    private CharacterCitiesPanel panel;

    public MainViewImpl() {
        addComponentsAndExpand(getMenuViewPanel());
        addComponentsAndExpand(getMainViewPanel());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        mainViewListeners.forEach(MainViewListener::entered);
    }

    private Panel getMenuViewPanel() {
        if (menuPanel == null) {
            menuPanel = new Panel();
        }
        return menuPanel;
    }

    private Panel getMainViewPanel() {
        if (mainViewPanel == null) {
            mainViewPanel = new Panel();
        }
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
    public void fillMenuPanel(AuthenticationService authenticationService) {
        getMenuViewPanel().setContent(new MenuComponent(authenticationService));
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
        this.panel = new CharacterCitiesPanel(cities);
        layout.addComponent(panel);
        mainViewPanel.setContent(layout);
    }

    @Override
    public void setSelectedCharacterButton(Button characterButton, List<City> cities) {
        this.panel.showCharacterButtonCities(characterButton, cities);
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

    private class CharacterCitiesPanel extends VerticalLayout {

        private final Panel citiesLinksPanel = new Panel();
        private final LinkedList<Button> characterButtons = new LinkedList<>();

        public CharacterCitiesPanel(List<City> cities) {
            super();
            super.addComponent(createAlphabetLayout(cities));
            super.addComponent(citiesLinksPanel);
            showCharacterButtonCities(characterButtons.getFirst().getCaption().charAt(0), cities);
        }

        public void showCharacterButtonCities(Character character, List<City> cities) {
            VerticalLayout verticalLayout = new VerticalLayout();
            for (City city : cities) {
                Button cityButton = new Button(city.getName());
                cityButton.addClickListener((Button.ClickListener) clickEvent -> mainViewListeners.forEach(listener -> listener.cityButtonClicked(city)));
                cityButton.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                verticalLayout.addComponent(cityButton);
            }
            citiesLinksPanel.setContent(verticalLayout);
        }

        private HorizontalLayout createAlphabetLayout(List<City> cities) {
            Set<Character> characters = new LinkedHashSet<>(
                    cities.stream()
                            .map(City::getName)
                            .map(cityName -> cityName.charAt(0))
                            .sorted(Character::compareTo)
                            .collect(Collectors.toList()));
            HorizontalLayout alphabetButtonsLayout = new HorizontalLayout();
            for (Character character : characters) {
                Button characterButton = new Button(character.toString());
                characterButton.addClickListener((Button.ClickListener) clickEvent -> mainViewListeners.forEach(listener -> listener.characterButtonClicked(characterButton.getCaption())));
                characterButton.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
                characterButtons.add(characterButton);
                alphabetButtonsLayout.addComponent(characterButton);
            }
            return alphabetButtonsLayout;
        }
    }
}