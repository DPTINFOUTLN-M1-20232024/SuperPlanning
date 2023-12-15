package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


/**
 *
 */
public class Controller {
    /**
     *
     */
    @FXML
    private void switchToWeekView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "WeekView.fxml");
    }
    @FXML
    private void switchToDailyView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "DailyView.fxml");
    }
}