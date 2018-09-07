package com.rustedbrain.study.course.view.authentication;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1171962991490071522L;

	private TabSheet tabSheet;

	private List<CinemaHallConstructorView.ViewListener> viewListeners = new ArrayList<>();

	@Autowired
	public CinemaHallConstructorViewImpl(AuthenticationService authenticationService) {
		addComponentsAndExpand(new Panel(new MenuComponent(authenticationService)));
		tabSheet = new TabSheet();
		tabSheet.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		addComponentsAndExpand(tabSheet);
	}

	@Override
	@Autowired
	public void addListener(CinemaHallConstructorView.ViewListener viewListener) {
		viewListener.setCinemaHallConstructorView(this);
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

	@Override
	public void reload() {
		Page.getCurrent().reload();
	}
	
	@Override
	public void addVerticalMenuComponents() {
		VerticalLayout verticalLayout = new VerticalLayout();
		Button seatsIconButton = new Button(VaadinIcons.ALIGN_JUSTIFY);
		seatsIconButton.setCaption("Seats");
		seatsIconButton.setWidth("110px");
		seatsIconButton.addClickListener(clickEvent -> {
			this.addComponent(getAddNewSeatsPopupView());
		});

		Button screenIconButton = new Button(VaadinIcons.PRESENTATION);
		screenIconButton.setCaption("Screen");
		screenIconButton.setWidth("110px");
		verticalLayout.addComponent(seatsIconButton);
		verticalLayout.addComponent(screenIconButton);

		this.tabSheet.addComponent(verticalLayout);
	}

	private PopupView getAddNewSeatsPopupView() {
		FormLayout content = new FormLayout();

		TextField numberOfRowsTextField = new TextField("Row");
		TextField numberOfSeatsTextField = new TextField("Seat");
		content.addComponent(numberOfRowsTextField);
		content.addComponent(numberOfSeatsTextField);
		content.addComponent(
				new Button("Add", (Button.ClickListener) event -> viewListeners.forEach(viewListener -> viewListener
						.addButtonClicked(numberOfRowsTextField.getValue(), numberOfSeatsTextField.getValue()))));
		content.setSizeUndefined();
		content.setMargin(true);

		PopupView createPopup = new PopupView(null, content);
		createPopup.setSizeUndefined();
		createPopup.setPopupVisible(true);
		return createPopup;
	}
}
