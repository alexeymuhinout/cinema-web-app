package com.rustedbrain.study.course.view.cinema;

import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

@SpringView(name = VaadinUI.HELP_VIEW)
public class HelpViewImpl extends NavigableView implements HelpView {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing view: Help!");
    }


    private Panel createHelpPanel() {
        Panel helpPanel = new Panel();
        FormLayout content = new FormLayout();

//        rem
//
//        content.addComponent(helpTree);
        content.setMargin(true);
        helpPanel.setContent(content);
        return helpPanel;
    }


    @Override
    public void showWarning(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void reloadPage() {

    }
}