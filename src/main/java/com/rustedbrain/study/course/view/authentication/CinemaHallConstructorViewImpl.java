package com.rustedbrain.study.course.view.authentication;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringView(name = VaadinUI.CINEMA_HALL_CONSTRUCTOR_VIEW)
public class CinemaHallConstructorViewImpl extends VerticalLayout implements CinemaHallConstructorView {
    private TabSheet tabSheet;


    private List<CinemaHallConstructorView.ViewListener> viewListeners = new ArrayList<>();

    @Autowired
    public CinemaHallConstructorViewImpl(AuthenticationService authenticationService) {
        addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
        tabSheet = new TabSheet();
        tabSheet.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        addComponentsAndExpand(tabSheet);
        addComponentsAndExpand(getVerticalComponents());
    }

    @Override
    @Autowired
    public void addListener(CinemaHallConstructorView.ViewListener viewListener) {
        viewListener.setCinemaHallConstructorView(this);
        viewListeners.add(viewListener);
    }

    private VerticalLayout getVerticalComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Button seatsIconButton = new Button(VaadinIcons.ALIGN_JUSTIFY);
        seatsIconButton.setCaption("Seats");
        seatsIconButton.addClickListener(clickEvent -> {
            addComponent(getAddNewSeatsPopupView());
        });
        verticalLayout.addComponent(seatsIconButton);
        return verticalLayout;
    }

    private PopupView getAddNewSeatsPopupView() {
        FormLayout content = new FormLayout();

        TextField numberOfRowsTextField = new TextField("Row");
        TextField numberOfSeatsTextField = new TextField("Seat");
        content.addComponent(numberOfRowsTextField);
        content.addComponent(numberOfSeatsTextField);
        content.addComponent(new Button("Add", (Button.ClickListener) event -> viewListeners.forEach(viewListener -> viewListener.addButtonClicked(numberOfRowsTextField.getValue(), numberOfSeatsTextField.getValue()))));
        content.setSizeUndefined();
        content.setMargin(true);

        PopupView createPopup = new PopupView(null, content);
        createPopup.setSizeUndefined();
        createPopup.setPopupVisible(true);
        return createPopup;
    }
}
