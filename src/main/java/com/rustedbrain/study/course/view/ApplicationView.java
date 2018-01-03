package com.rustedbrain.study.course.view;

public interface ApplicationView {

    void showWarning(String message);

    void showError(String message);

    void reloadPage();
}
