package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import java.io.IOException;

public class WeekViewController {

    /* MODEL PART */


    /* GUI PART */
    /* User informations */
    @FXML
    private Label nameUser;

    @FXML
    private Label firstNameUser;

    @FXML
    private Label promotionUser;

    /* WeekView Planning informations */
    @FXML
    private Pagination weekPagination;

    @FXML
    private Label mondayDate;

    @FXML
    private Label tuesdayDate;

    @FXML
    private Label wednesdayDate;

    @FXML
    private Label thursdayDate;

    @FXML
    private Label fridayDate;

    /* Methods */
    @FXML
    private void switchToDailyView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "DailyView.fxml");
    }

    private void showWeekSessions() {
        /*
        Shows every sessions of the week for a selected student.
         */
        // todo : Implanter ce code avec des objets de la classe SessionGUI

    }

}
