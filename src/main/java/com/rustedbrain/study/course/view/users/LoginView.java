package com.rustedbrain.study.course.view.users;

import com.rustedbrain.study.course.controller.service.AuthorizationService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = VaadinUI.LOGIN_VIEW)
public class LoginView extends VerticalLayout implements View {

    public static final String LOGINED_USER_ATRIBUTE = "user";
    @Autowired
    private AuthorizationService authorizationService;
    private TextField loginTextField;
    private TextField passwordTextField;

    public LoginView() {
        Panel panel = new Panel("Login");
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        loginTextField = new TextField("Login");
        content.addComponent(loginTextField);
        passwordTextField = new PasswordField("Password");
        content.addComponent(passwordTextField);

        Button send = new Button("Enter");
        send.addClickListener(clickEvent -> {
            if (authorizationService.isRegisteredUser(loginTextField.getValue(), passwordTextField.getValue())) {
                VaadinSession.getCurrent().setAttribute("user", loginTextField.getValue());
                getUI().getNavigator().addView(VaadinUI.PROFILE_VIEW, ProfileView.class);
                Page.getCurrent().setUriFragment("!" + VaadinUI.MAIN_VIEW);
            } else {
                Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
            }
        });

        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}