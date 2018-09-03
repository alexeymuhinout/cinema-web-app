package com.rustedbrain.study.course.view.authentication.layout;

import com.rustedbrain.study.course.model.persistence.cinema.Cinema;
import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.view.authentication.ProfileView;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdministrationCinemaHallPanel extends Panel {
    protected VerticalLayout layout = new VerticalLayout();
    private List<ProfileView.ViewListener> listeners;
    private List<City> cities = new ArrayList<>();
    private List<CinemaHall> cinemaHalls = new ArrayList<>();
    private Grid<CinemaHall> grid = new Grid<>();
    private List<Cinema> cinemas = new ArrayList<>();

    public AdministrationCinemaHallPanel(List<ProfileView.ViewListener> listeners, List<City> cities) {
        this.listeners = listeners;
        this.cities = cities;
        this.cities.forEach(city -> cinemas.addAll(city.getCinemas()));
        this.cities.forEach(city -> city.getCinemas().forEach(cinema -> cinemaHalls.addAll(cinema.getCinemaHalls())));
        this.layout.addComponent(new Panel(showCinemaHallSelectionPanel(cinemaHalls)));
        setContent(this.layout);
    }

    private Layout showCinemaHallSelectionPanel(List<CinemaHall> cinemaHalls) {
        VerticalLayout mainLayout = new VerticalLayout();

        grid.setItems(cinemaHalls);
        grid.addColumn(CinemaHall::getName).setCaption("Name");
        grid.addColumn(cinemaHall -> cinemaHall.getCinema().getName()).setCaption("Cinema");
        grid.addColumn(cinemaHall -> cinemaHall.getCinema().getCity().getName()).setCaption("City");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setSizeFull();

        TextField filterTextField = new TextField();
        filterTextField.setPlaceholder("Filter by cinema name...");
        filterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        filterTextField.addValueChangeListener(event ->
                getFilteredByCinemaHallName(event.getValue())
        );

        Button clearFilterTextButton = new Button(VaadinIcons.CLOSE);
        clearFilterTextButton.setDescription("clear the current filter");
        clearFilterTextButton.addClickListener(clickEvent -> filterTextField.clear());

        HorizontalLayout filterLayout = new HorizontalLayout(filterTextField, clearFilterTextButton);
        filterLayout.setSpacing(false);

        Button addNewCinemaButton = new Button("Add new cinema hall");

        addNewCinemaButton.addClickListener(clickEvent -> {
            grid.deselectAll();
            filterLayout.addComponent(addNewCinemaHallButtonClick());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterLayout, addNewCinemaButton);


        SaveDeleteForm saveDeleteForm = new SaveDeleteForm();

        grid.addSelectionListener(selectionEvent -> {
            if (grid.getSelectionModel().getFirstSelectedItem().isPresent()) {
                saveDeleteForm.setSelectedCinemaHall(selectionEvent.getFirstSelectedItem().get());
                saveDeleteForm.setVisible(true);
            } else {
                saveDeleteForm.setVisible(false);
            }
        });
        saveDeleteForm.setVisible(false);

        HorizontalLayout gridFormLayout = new HorizontalLayout(grid, saveDeleteForm);
        gridFormLayout.setSizeFull();
        gridFormLayout.setExpandRatio(grid, 1);

        mainLayout.addComponents(toolbar, gridFormLayout);

        return mainLayout;
    }

    private PopupView addNewCinemaHallButtonClick() {
        VerticalLayout createPopupContent = new VerticalLayout();

        TextField cinemaHallName = new TextField("Cinema Hall name");
        ComboBox<City> cityComboBox = new ComboBox<>("City");
        ComboBox<Cinema> cinemaComboBox = new ComboBox<>("Cinema");

        cityComboBox.setEmptySelectionAllowed(false);
        cityComboBox.setItems(cities);
        cityComboBox.setItemCaptionGenerator(City::getName);
        cityComboBox.setSelectedItem(cities.get(0));
        cityComboBox.addValueChangeListener(valueChangeEvent -> {
            Set<Cinema> cinemas = valueChangeEvent.getValue().getCinemas();
            cinemaComboBox.setEmptySelectionAllowed(false);
            cinemaComboBox.setItems(cinemas);
            cinemaComboBox.setItemCaptionGenerator(Cinema::getName);
            cinemaComboBox.setSelectedItem(cinemas.iterator().next());
        });

        cinemaComboBox.setEmptySelectionAllowed(false);
        cinemaComboBox.setItems(cinemas.stream().filter(cinema -> cinema.getCity().equals(cities.get(0))));
        cinemaComboBox.setItemCaptionGenerator(Cinema::getName);

        createPopupContent.addComponent(cinemaHallName);
        createPopupContent.addComponent(cityComboBox);
        createPopupContent.addComponent(cinemaComboBox);

        createPopupContent.addComponent(new Button("Next", (Button.ClickListener) event -> {
            listeners.forEach(cinemaHallViewListener -> {
                if (cityComboBox.getSelectedItem().isPresent()) {
                    if (cinemaComboBox.getSelectedItem().isPresent()) {
                        if (!cinemaHallName.getValue().isEmpty()) {
                            cinemaHallViewListener.getCinemaHallEditPresenter().buttonAddNewCinemaHallClicked(cinemaHallName.getValue(), cityComboBox.getSelectedItem(), cinemaComboBox.getSelectedItem());
                            new PageNavigator().navigateToCinemaHallConstructorView();
                        } else {
                            Notification.show("Please enter name", "", Notification.Type.ERROR_MESSAGE);
                        }
                    } else {
                        Notification.show("Please select cinema", "", Notification.Type.ERROR_MESSAGE);
                    }
                } else {
                    Notification.show("Please select City", "", Notification.Type.ERROR_MESSAGE);
                }
            });
        }));

        PopupView createPopup = new PopupView(null, createPopupContent);
        createPopup.setSizeUndefined();
        createPopup.setPopupVisible(true);
        return createPopup;
    }

    private void getFilteredByCinemaHallName(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(cinemaHalls);
        } else {
            List<CinemaHall> filteredCinemaHalls = cinemaHalls.stream().filter(cinemaHall -> cinemaHall.getName().contains(filterText)).collect(Collectors.toList());
            grid.setItems(filteredCinemaHalls);
        }
    }

    private class SaveDeleteForm extends FormLayout {
        private CinemaHall selectedCinemaHall;
        private TextField cinemaHallNameTextField;
        private ComboBox<Cinema> cinemaComboBox;
        private Button saveButton;
        private Button deleteButton;

        private SaveDeleteForm() {
            cinemaHallNameTextField = new TextField("Cinema hall name");
            cinemaComboBox = new ComboBox<>("Cinema");
            saveButton = new Button("Save");
            deleteButton = new Button("Delete");
            saveButton.addStyleName("friendly");
            deleteButton.addStyleName("danger");
            setSizeUndefined();

            HorizontalLayout buttons = new HorizontalLayout(saveButton, deleteButton);
            addComponents(cinemaHallNameTextField, cinemaComboBox, buttons);

            deleteButton.addClickListener(clickEvent -> {
                this.deleteCinemaHall(this.selectedCinemaHall);
                Notification.show("Cinema hall deleted", "Cinema hall name: " + this.selectedCinemaHall.getName(), Notification.Type.HUMANIZED_MESSAGE);
            });
            saveButton.addClickListener(clickEvent -> {
                this.editCinema(this.selectedCinemaHall, cinemaHallNameTextField.getValue(), cinemaComboBox.getSelectedItem().get());
                Notification.show("Cinema hall edit", "Cinema hall name: " + this.selectedCinemaHall.getName(), Notification.Type.HUMANIZED_MESSAGE);
            });
        }

        private void deleteCinemaHall(CinemaHall selectedCinemaHall) {
            listeners.forEach(listener -> listener.getCinemaHallEditPresenter().buttonDeleteCinemaHallClicked(selectedCinemaHall.getId()));
            this.setVisible(false);
            cinemaHalls.remove(selectedCinemaHall);
            grid.setItems(cinemaHalls);
        }

        private void editCinema(CinemaHall selectedCinemaHall, String newCinemaHallName, Cinema newCinema) {
            listeners.forEach(listener -> listener.getCinemaHallEditPresenter().buttonSaveCinemaHallClicked(selectedCinemaHall, newCinemaHallName, newCinema));
        }

        public void setSelectedCinemaHall(CinemaHall selectedCinemaHall) {
            this.selectedCinemaHall = selectedCinemaHall;
            cinemaHallNameTextField.setValue(selectedCinemaHall.getName());

            cinemaComboBox.setItems(cinemas);
            cinemaComboBox.setItemCaptionGenerator(Cinema::getName);
            cinemaComboBox.setEmptySelectionAllowed(false);
            cinemaComboBox.setSelectedItem(selectedCinemaHall.getCinema());
        }
    }
}
