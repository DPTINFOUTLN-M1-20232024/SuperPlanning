package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;

public class DailyViewController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button nextDayButton;
    @FXML
    private Button prevDayButton;
    public void initialize() {
// initialise la date actuelle
        datePicker.setValue(LocalDate.now());

    }
    @FXML
    private void nextDay(ActionEvent event) {
//recupere la date actuelle
        LocalDate currentDate = datePicker.getValue();
//+un jour
        datePicker.setValue(currentDate.plusDays(1));
    }
    @FXML
    private void prevDay(ActionEvent event) {

        LocalDate currentDate = datePicker.getValue();

        datePicker.setValue(currentDate.minusDays(1));



    }

    @FXML
    private void switchToWeekView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "WeekView.fxml");
    }
    @FXML
    private void switchToDailyView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "DailyView.fxml");
    }

}
