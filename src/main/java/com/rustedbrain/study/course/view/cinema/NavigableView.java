package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.view.ApplicationView;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.Menu;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = VaadinUI.MAIN_VIEW)
public class NavigableView extends VerticalLayout implements View, ApplicationView {

    public NavigableView() {
        setSizeFull();
        setSpacing(true);
        addComponent(createMenuPanel());
    }

    private Panel createMenuPanel() {
        FormLayout content = new FormLayout();
        content.addComponent(new Menu());
        content.setMargin(true);
        return new Panel(content);
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
}