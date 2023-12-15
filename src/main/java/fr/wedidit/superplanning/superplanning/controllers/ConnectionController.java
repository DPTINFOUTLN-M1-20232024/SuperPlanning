package fr.wedidit.superplanning.superplanning.controllers;

import fr.wedidit.superplanning.superplanning.account.Account;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.StudentConnection;
import fr.wedidit.superplanning.superplanning.vues.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ConnectionController {

    private static final String POPUP_NAME = "Connection";

    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldMail;

    public void onButtonConnectClick(ActionEvent actionEvent) {
        String mail = textFieldMail.getText();
        String password = textFieldPassword.getText();

        // Loading the student from (mail, password)
        Student student;
        StudentConnection studentConnection = StudentConnection.of(mail, password);
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            student = studentConnectionDAO.getStudentFromConnection(studentConnection);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            Popup.popup(POPUP_NAME, dataAccessException.getLocalizedMessage());
            return;
        }

        // Check if mail and password is correct
        if (student == null) {
            String messageError = "Mail or password is not correct";
            log.error(messageError);
            Popup.popup(POPUP_NAME, messageError);
            return;
        }

        // Swap to the next view
        Account.connect(student);
        String fxmlFileName = "DailyView.fxml";
        try {
            SceneSwitcher.switchToScene(actionEvent, fxmlFileName);
        } catch (IOException e) {
            Account.disconnect();
            String messageError = "Error while looking for file \"%s\": %s%nYou have been disconnected".formatted(fxmlFileName, e.getLocalizedMessage());
            log.error(messageError);
            Popup.popup(POPUP_NAME, messageError);
        }
    }
}
