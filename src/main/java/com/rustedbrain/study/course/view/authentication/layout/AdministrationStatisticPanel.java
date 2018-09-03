package com.rustedbrain.study.course.view.authentication.layout;

import com.rustedbrain.study.course.view.authentication.ProfileView;
import com.vaadin.ui.*;

import java.util.List;

public class AdministrationStatisticPanel extends Panel {

    protected VerticalLayout layout = new VerticalLayout();
    private List<ProfileView.ViewListener> listeners;


    public AdministrationStatisticPanel(List<ProfileView.ViewListener> listeners) {
        this.listeners = listeners;
        this.layout.addComponent(new Panel(showStatisticPanel()));
        setContent(this.layout);
    }


    private Layout showStatisticPanel() {
        final AbsoluteLayout layout = new AbsoluteLayout();
        layout.setWidth("800px");
        layout.setHeight("400px");


        Button button1 = new Button("1");
        button1.setHeight("40px");
        button1.setWidth("100px");
        layout.addComponent(button1, "left: 0px; top: 0px;");

        Button button2 = new Button("2");
        button2.setHeight("40px");
        button2.setWidth("300px");
        layout.addComponent(button2, "left: 0px; top: 50px;");

        return layout;
    }
}
