package com.rustedbrain.study.course.view.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.rustedbrain.study.course.model.persistence.cinema.CinemaHall;
import com.rustedbrain.study.course.service.AuthenticationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.MenuComponent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringView(name = VaadinUI.CINEMA_HALL_CONSTRUCTOR_VIEW)
public class CinemaHallConstructorViewImpl extends VerticalLayout implements CinemaHallConstructorView {
	private static final long serialVersionUID = 1171962991490071522L;

	private CinemaHall cinemaHall;

	private TabSheet tabSheet;
	private Panel cinemaHallConstructorPanel;
	private Panel componentMenuPanel;

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
	public void addCinemaHallConstructorMenuComponents(CinemaHall cinemaHall) {
		this.cinemaHall = cinemaHall;
		final HorizontalLayout content = new HorizontalLayout();
		Panel componentMenuPanel = getCinemaHallComponentMenuPanel();
		Panel cinemaHallConstructorPanel = getCinemaHallConstructorPanel();

		VerticalLayout componentMenuLayout = new VerticalLayout();
		Button saveCinemaHallSeatsButton = new Button("Save cinema hall");
		saveCinemaHallSeatsButton
				.addClickListener(clickEvent -> UI.getCurrent().addWindow(getSaveCinemaHallSeatsConformationWindow()));
		Button addRowButton = new Button(VaadinIcons.ALIGN_JUSTIFY);
		addRowButton.setCaption("Add seats");
		addRowButton.setWidth("160px");
		addRowButton.addClickListener(clickEvent -> UI.getCurrent().addWindow(getAddNewSeatsWindow()));
		componentMenuLayout.addComponentsAndExpand(addRowButton, saveCinemaHallSeatsButton);
		componentMenuPanel.setContent(componentMenuLayout);

		content.addComponents(componentMenuPanel, cinemaHallConstructorPanel);
		tabSheet.addTab(content, "Cinema hall constructor");
	}

	private Window getSaveCinemaHallSeatsConformationWindow() {
		Window saveCinemaHallConformationWindow = new Window();
		saveCinemaHallConformationWindow.setHeight("100px");
		saveCinemaHallConformationWindow.setWidth("180px");
		saveCinemaHallConformationWindow.setModal(true);
		saveCinemaHallConformationWindow.setResizable(false);
		saveCinemaHallConformationWindow.setDraggable(false);

		Label saveLabel = new Label("Save changes?");
		saveLabel.setSizeFull();
		Button saveButton = new Button("Save", (Button.ClickListener) event -> {
			viewListeners.forEach(listener -> listener.buttonSaveCinemaHallSeatsButtonClicked());
			saveCinemaHallConformationWindow.close();
			showNotification("Cinema Hall saved ");
		});
		saveButton.addStyleName("friendly");

		VerticalLayout saveButtonLayout = new VerticalLayout();
		saveButtonLayout.addComponentsAndExpand(saveLabel, saveButton);
		saveCinemaHallConformationWindow.setContent(saveButtonLayout);

		return saveCinemaHallConformationWindow;
	}

	private void showNotification(String message) {
		Notification notification = new Notification(message, Notification.Type.HUMANIZED_MESSAGE);
		notification.show(Page.getCurrent());
	}

	private Panel getCinemaHallConstructorPanel() {
		if ( cinemaHallConstructorPanel == null ) {
			cinemaHallConstructorPanel = new Panel();
		}
		return cinemaHallConstructorPanel;
	}

	private Panel getCinemaHallComponentMenuPanel() {
		if ( componentMenuPanel == null ) {
			componentMenuPanel = new Panel();
		}
		return componentMenuPanel;
	}

	private Window getAddNewSeatsWindow() {
		Window addNewSeatsWindow = new Window("Add seats");
		addNewSeatsWindow.setModal(true);
		addNewSeatsWindow.setResizable(false);
		addNewSeatsWindow.setDraggable(false);

		FormLayout content = new FormLayout();
		TextField numberOfRowsTextField = new TextField("Row number");
		TextField numberOfSeatsTextField = new TextField("Number of seats");
		content.addComponent(numberOfRowsTextField);
		content.addComponent(numberOfSeatsTextField);
		content.addComponent(new Button("Add", (Button.ClickListener) event -> {
			viewListeners.forEach(viewListener -> viewListener.addSeatsButtonClicked(cinemaHall.getId(),
					numberOfRowsTextField.getValue(), numberOfSeatsTextField.getValue()));
		}));
		content.setSizeUndefined();
		content.setMargin(true);
		addNewSeatsWindow.setContent(content);
		return addNewSeatsWindow;
	}

	@Override
	public void setCinemaHallSeatMap(Map<Integer, List<Integer>> cinemaHallSeatCoordinateMultiMap) {
		final VerticalLayout hallLayout = new VerticalLayout();
		final AbsoluteLayout seatsLayout = new AbsoluteLayout();
		seatsLayout.setHeight("500px");
		seatsLayout.setWidth("700px");
		cinemaHallSeatCoordinateMultiMap.entrySet().stream().forEach(enty -> {
			int key = enty.getKey();
			enty.getValue().forEach(value -> {
				Button button = new Button(String.valueOf(value + 1));
				button.setWidth("50px");
				button.setDescription(
						"<ul>" + "<li>Row: " + (key + 1) + "</li>" + "<li>Seat: " + (value + 1) + "</li>" + "</ul>",
						ContentMode.HTML);
				seatsLayout.addComponent(button, "top: " + 50 * key + "px;" + "left: " + 60 * value + "px;");
			});

		});
		Button screen = new Button("Screen");
		screen.setWidth("550px");
		hallLayout.addComponents(screen, seatsLayout);
		getCinemaHallConstructorPanel().setContent(hallLayout);
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

}
