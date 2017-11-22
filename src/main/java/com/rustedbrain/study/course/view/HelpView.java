package com.rustedbrain.study.course.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

@SpringView(name = VaadinUI.HELP_VIEW)
public class HelpView extends MainView {

    private Panel currentPanel;

    public HelpView() {
        super();
        reloadCurrentPanel(createHelpPanel());
    }

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

    private void reloadCurrentPanel(Panel newPanel) {
        if (currentPanel != null) {
            removeComponent(currentPanel);
        }
        currentPanel = newPanel;
        addComponent(currentPanel);
    }


}