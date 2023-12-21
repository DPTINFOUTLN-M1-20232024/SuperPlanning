package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import fr.wedidit.superplanning.superplanning.identifiables.others.SessionType;
import fr.wedidit.superplanning.superplanning.utils.others.TimeUtils;
import fr.wedidit.superplanning.superplanning.utils.vues.AutoCompleteBox;
import fr.wedidit.superplanning.superplanning.utils.vues.Popup;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class SecretarySessionController {
    public TextField textFieldStartTime;
    public TextField textFieldEndTime;
    public DatePicker datePickerStartDate;
    public DatePicker datePickerEndDate;
    public ComboBox<Instructor> comboBoxInstructor;
    public ComboBox<Module> comboBoxModule;
    public ComboBox<Grade> comboBoxGrade;
    public ComboBox<SessionType> comboBoxSessionType;

    @FXML
    private void initialize() {
        try (InstructorDAO instructorDAO = new InstructorDAO()) {

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            Popup.error(dataAccessException.getLocalizedMessage());
        }
        new AutoCompleteBox<Instructor>(comboBoxInstructor);
        new AutoCompleteBox<Module>(comboBoxModule);
        new AutoCompleteBox<Grade>(comboBoxGrade);
        List<SessionType> sessionsType = List.of(SessionType.values());
        comboBoxSessionType.setItems(FXCollections.observableList(sessionsType));
    }

    public void onAddSession(ActionEvent actionEvent) {
        Timestamp dateTimeStartSession = parseTime(datePickerStartDate, textFieldStartTime);
        Timestamp dateTimeEndSession = parseTime(datePickerEndDate, textFieldEndTime);


    }

    private Timestamp parseTime(DatePicker datePicker, TextField textField) {
        int[] time;
        try {
            time = TimeUtils.parseHoursMinutes(textField.getText());
        } catch (NumberFormatException numberFormatException) {
            Popup.error("Le format de la date de cours n'est pas correct.\nFormat: 14h30 ou 14:30");
            return null;
        }

        return Timestamp.valueOf(datePicker.getValue().atTime(time[0], time[1]));
    }

}
