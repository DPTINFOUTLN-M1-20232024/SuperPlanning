package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.ActionEvent;

public class SecretaryManagementController {

    public void addInstructor(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderInstructor");
    }

    public void addSession(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderSession");
    }

    public void addModule(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderModule");
    }

    public void addGrade(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderGrade");
    }

    public void addStudent(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderStudent");
    }
}
