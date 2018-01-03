package com.rustedbrain.study.course.view.util;

import com.rustedbrain.study.course.view.VaadinUI;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;

import java.util.Optional;

public class NotificationUtil {

    public static void showAvailableMessage() {
        if (Optional.ofNullable(VaadinSession.getCurrent().getAttribute(VaadinUI.MESSAGE_ATTRIBUTE)).isPresent()) {
            Notification.show(String.valueOf(VaadinSession.getCurrent().getAttribute(VaadinUI.MESSAGE_ATTRIBUTE)), Notification.Type.HUMANIZED_MESSAGE);
            clearMessage();
        }
    }

    public static void setMessage(String message) {
        VaadinSession.getCurrent().setAttribute(VaadinUI.MESSAGE_ATTRIBUTE, message);
    }

    public static void clearMessage() {
        VaadinSession.getCurrent().setAttribute(VaadinUI.MESSAGE_ATTRIBUTE, null);
    }
}
