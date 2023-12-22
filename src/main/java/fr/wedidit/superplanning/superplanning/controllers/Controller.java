package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 */
public class Controller {
    /**
     *
     */
    @FXML
    private void switchToWeekView(ActionEvent event) {
        SceneSwitcher.switchToScene(event, "WeekView.fxml");
    }
    @FXML
    private void switchToDailyView(ActionEvent event) {
        SceneSwitcher.switchToScene(event, "DailyView.fxml");
    }
}