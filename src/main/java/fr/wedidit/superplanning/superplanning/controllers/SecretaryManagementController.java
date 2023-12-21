package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.ActionEvent;

public class SecretaryManagementController {

    public void addInstructor(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderInstructor.fxml");
    }

    public void addSession(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderSession.fxml");
    }

    public void addModule(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderModule.fxml");
    }

    public void addGrade(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderGrade.fxml");
    }

    public void addStudent(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryAdderStudent.fxml");
    }
}
