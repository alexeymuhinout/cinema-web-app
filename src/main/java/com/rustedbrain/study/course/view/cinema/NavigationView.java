package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.Menu;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = VaadinUI.MAIN_VIEW)
public class NavigationView extends VerticalLayout implements View {

    public NavigationView() {
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
}