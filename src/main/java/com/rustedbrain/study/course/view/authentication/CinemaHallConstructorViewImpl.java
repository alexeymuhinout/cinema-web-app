package com.rustedbrain.study.course.view.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView(name = VaadinUI.CINEMA_HALL_CONSTRUCTOR_VIEW)
public class CinemaHallConstructorViewImpl extends VerticalLayout implements CinemaHallConstructorView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1171962991490071522L;

	private TabSheet tabSheet;
	private Panel cinemaHallConstructorPanel;
	private Panel menuPanel;

	private List<CinemaHallConstructorView.ViewListener> viewListeners = new ArrayList<>();
	private Map<Integer, Integer> rowsSeatsMap = new HashMap<>();

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
	public void addContent() {
		HorizontalLayout content = new HorizontalLayout();
		Panel componentMenuPanel = new Panel();
		Panel cinemaHallconstructorPanel = getCinemaHallConstructorPanel();
		
		VerticalLayout componentMenuLayout = new VerticalLayout();
		Button seatsIconButton = new Button(VaadinIcons.ALIGN_JUSTIFY);
		seatsIconButton.setCaption("Seats");
		seatsIconButton.setWidth("110px");
		seatsIconButton.addClickListener(clickEvent -> {
			this.addComponent(getAddNewSeatsPopupView());
		});

		Button screenIconButton = new Button(VaadinIcons.PRESENTATION);
		screenIconButton.setCaption("Screen");
		screenIconButton.setWidth("110px");
		componentMenuLayout.addComponent(seatsIconButton);
		componentMenuLayout.addComponent(screenIconButton);
		componentMenuPanel.setContent(componentMenuLayout);
		
		content.addComponents(componentMenuPanel, cinemaHallconstructorPanel);

		tabSheet.addTab(content, "Cinema hall constructor");
	}

	private Panel getCinemaHallConstructorPanel() {
		if (cinemaHallConstructorPanel == null) {
			cinemaHallConstructorPanel = new Panel();
		}
		return cinemaHallConstructorPanel;
	}
	
	private PopupView getAddNewSeatsPopupView() {
		FormLayout content = new FormLayout();

		TextField numberOfRowsTextField = new TextField("Row");
		TextField numberOfSeatsTextField = new TextField("Seat");
		content.addComponent(numberOfRowsTextField);
		content.addComponent(numberOfSeatsTextField);
		content.addComponent(new Button("Add", (Button.ClickListener) event -> {
			viewListeners.forEach(viewListener -> viewListener.addButtonClicked(numberOfRowsTextField.getValue(),
					numberOfSeatsTextField.getValue()));
			for (int i = 0; i < Integer.parseInt(numberOfRowsTextField.getValue()); i++) {
				for (int j = 0; j < Integer.parseInt(numberOfSeatsTextField.getValue()); j++) {
					rowsSeatsMap.put(i + 1, j + 1);
				}
			}
		}));
		content.setSizeUndefined();
		content.setMargin(true);

		PopupView createPopup = new PopupView(null, content);
		createPopup.setSizeUndefined();
		createPopup.setPopupVisible(true);
		return createPopup;
	}

	@Override
	public void setCinemaHallSeatMap(Map<Integer, Integer> cinemaHallSeatMap) {
		Accordion helpAccordion = new Accordion();
		for (Map.Entry<Integer, Integer> entry : cinemaHallSeatMap.entrySet()) {
			int x = entry.getKey();
			int y = entry.getValue();
			Label label = new Label(String.valueOf(entry.getValue()));
			label.setWidth(100.0f, Unit.PERCENTAGE);

			final VerticalLayout layout = new VerticalLayout(label);
			layout.setMargin(true);

			helpAccordion.addTab(layout, entry.getKey());
		}
		getCinemaHallConstructorPanel().setContent(helpAccordion);
	}
}
