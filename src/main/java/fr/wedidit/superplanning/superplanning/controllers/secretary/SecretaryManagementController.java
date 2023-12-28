package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.account.AccountSecretary;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SecretaryManagementController {

    @FXML
    public void addInstructor(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_ADDER_INSTRUCTOR);
    }

    @FXML
    public void addSession(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_ADDER_SESSION);
    }

    @FXML
    public void addModule(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_ADDER_MODULE);
    }

    @FXML
    public void addGrade(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_ADDER_GRADE);
    }

    @FXML
    public void addStudent(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_ADDER_STUDENT);
    }

    @FXML
    public void onButtonDisconnect(ActionEvent actionEvent) {
        AccountSecretary.disconnect();
        SceneSwitcher.switchToScene(actionEvent, Views.CONNECTION);
    }
}
