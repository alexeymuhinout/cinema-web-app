package com.rustedbrain.study.course.view;

import com.rustedbrain.study.course.model.cinema.City;
import com.rustedbrain.study.course.model.cinema.Movie;
import com.rustedbrain.study.course.presenter.cinema.MainViewPresenter;
import com.rustedbrain.study.course.view.authentication.LoginViewImpl;
import com.rustedbrain.study.course.view.cinema.NavigableView;
import com.rustedbrain.study.course.view.components.CityComboBox;
import com.rustedbrain.study.course.view.util.NotificationUtil;
import com.rustedbrain.study.course.view.util.PageNavigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UIScope
@SpringView(name = VaadinUI.MAIN_VIEW)
public class MainViewImpl extends NavigableView implements MainView {

    private Collection<MainViewListener> mainViewListeners = new ArrayList<>();

    private ComboBox<City> cityComboBox;
    private VerticalLayout moviesLayout;

    public MainViewImpl() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(createMoviesPanel());
        addComponentsAndExpand(layout);
    }

    @Autowired
    public void initPresenter(MainViewPresenter presenter) {
        presenter.setView(this);
        presenter.bind();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        NotificationUtil.showAvailableMessage();
    }

    private Panel createMoviesPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();

        HorizontalLayout layoutLabelAndFind = new HorizontalLayout();

        cityComboBox = new CityComboBox();
        cityComboBox.setSizeFull();

        layoutLabelAndFind.addComponentsAndExpand(cityComboBox);

        Button button = new Button("Go");
        button.addClickListener((Button.ClickListener) event -> new PageNavigator().navigateToCityView(cityComboBox.getValue()));
        button.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        layoutLabelAndFind.addComponent(button);

        verticalLayout.addComponentsAndExpand(layoutLabelAndFind, createCityAndCinemasAccordion());

        if (VaadinSession.getCurrent().getAttribute(LoginViewImpl.LOGGED_ADMINISTRATOR_ATTRIBUTE) != null) {
            verticalLayout.addComponentsAndExpand(createAdminCityAddingPanel());
        }

        Panel panel = new Panel(verticalLayout);
        panel.setWidth(70, Unit.PERCENTAGE);
        return panel;
    }

    private Component createCityAndCinemasAccordion() {
        moviesLayout = new VerticalLayout(new Label("Cities&Cinemas"));
        return moviesLayout;
    }

    private Component createAdminCityAddingPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        TextField textFieldCityName = new TextField("Name");
        horizontalLayout.addComponentsAndExpand(textFieldCityName);

        verticalLayout.addComponentsAndExpand(horizontalLayout);
        Button buttonCreateCity = new Button("Create city", event -> mainViewListeners.forEach(x -> x.buttonCreateCityClicked(textFieldCityName.getValue())));
        buttonCreateCity.setSizeFull();
        verticalLayout.addComponentsAndExpand(buttonCreateCity);
        Panel panel = new Panel(verticalLayout);
        panel.setSizeFull();
        return panel;
    }

    @Override
    public void fillMoviesPanel(List<Movie> movies) {

    }

    @Override
    @Autowired
    public void addMainViewListener(MainViewListener mainViewListener) {
        this.mainViewListeners.add(mainViewListener);
    }


}