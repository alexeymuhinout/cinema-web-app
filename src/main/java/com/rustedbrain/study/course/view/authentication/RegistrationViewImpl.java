package com.rustedbrain.study.course.view.authentication;

import com.rustedbrain.study.course.model.persistence.cinema.City;
import com.rustedbrain.study.course.service.AuthorizationUserService;
import com.rustedbrain.study.course.service.CinemaService;
import com.rustedbrain.study.course.view.VaadinUI;
import com.rustedbrain.study.course.view.components.CityComboBox;
import com.vaadin.data.Result;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@SpringView(name = VaadinUI.REGISTRATION_VIEW)
public class RegistrationViewImpl extends VerticalLayout implements View {

    @Autowired
    CinemaService cinemaService;
    @Autowired
    AuthorizationUserService authorizationUserService;
    private TextField loginTextField;
    private TextField nameTextField;
    private TextField surnameTextField;
    private DateField birthdayDateField;
    private ComboBox<City> cityComboBox;
    private TextField mailTextField;
    private TextField passwordTextField;

    private String getNonEmptyFieldValue(TextField field, String nullCaseError) {
        if (field.isEmpty()) {
            throw new IllegalArgumentException(nullCaseError);
        }
        return field.getValue();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Panel panel = new Panel("Registration");
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        loginTextField = new TextField("Login");
        content.addComponent(loginTextField);

        passwordTextField = new PasswordField("Password");
        content.addComponent(passwordTextField);

        nameTextField = new TextField("Name");
        content.addComponent(nameTextField);

        surnameTextField = new TextField("Surname");
        content.addComponent(surnameTextField);

        cityComboBox = new CityComboBox(cinemaService.getCities(), "City");
        content.addComponent(cityComboBox);

        birthdayDateField = new DateField("Birthday") {
            @Override
            protected Result<LocalDate> handleUnparsableDateString(
                    String dateString) {
                try {
                    // try to parse with alternative format
                    LocalDate parsedAtServer = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    return Result.ok(parsedAtServer);
                } catch (DateTimeParseException e) {
                    return Result.error("Bad date format");
                }
            }
        };
        content.addComponent(birthdayDateField);

        mailTextField = new TextField("Mail");
        content.addComponent(mailTextField);

//        Button register = new Button("Register");
//        register.addClickListener(clickEvent -> {
//            if (authorizationUserService.isUniqueUser(loginTextField.getValue(), mailTextField.getValue())) {
//                try {
//                    String login = getNonEmptyFieldValue(loginTextField, "Login cannot be empty");
//                    String password = getNonEmptyFieldValue(passwordTextField, "Password cannot be empty");
//                    String email = getNonEmptyFieldValue(mailTextField, "Mail cannot be empty");
//                    String name = nameTextField.getValue();
//                    String surname = surnameTextField.getValue();
//                    City city = cityComboBox.getValue();
//                    LocalDate birthday = birthdayDateField.getValue();
//
//                    Member member = new Member();
//                    member.setName(name);
//                    member.setSurname(surname);
//                    member.setEmail(email);
//                    member.setLogin(login);
//                    member.setPassword(password);
//                    member.setCity(city);
//                    member.setBirthday(Date.valueOf(birthday));
//
//                    authorizationUserService.registerMember(member);
//                    Page.getCurrent().setUriFragment("!" + VaadinUI.NAVIGATION_MENU_VIEW);
//                } catch (IllegalArgumentException ex) {
//                    Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
//                }
//            } else {
//                Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
//            }
//        });
//
//        register.setSizeFull();
//
//        content.addComponent(register);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }
}