package fr.wedidit.superplanning.superplanning.controllers;

import fr.wedidit.superplanning.superplanning.account.AccountSecretary;
import fr.wedidit.superplanning.superplanning.account.AccountStudent;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.SecretaryConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.SessionConnection;
import fr.wedidit.superplanning.superplanning.vues.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

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
        SessionConnection sessionConnection = SessionConnection.of(mail, password);
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            student = studentConnectionDAO.getStudentFromConnection(sessionConnection);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            Popup.popup(POPUP_NAME, dataAccessException.getLocalizedMessage());
            return;
        }

        // Check if mail and password is correct
        if (student == null) {

            try (SecretaryConnectionDAO secretaryConnectionDAO = new SecretaryConnectionDAO()) {
                if (secretaryConnectionDAO.getSecretaryFromConnection(sessionConnection)) {
                    AccountSecretary.logging();
                    SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
                    return;
                } else {
                    log.info("pas trouvée");
                }
            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getLocalizedMessage());
                Popup.popup(POPUP_NAME, dataAccessException.getLocalizedMessage());
            }

            String messageError = "Mail or password is not correct";
            log.error(messageError);
            Popup.popup(POPUP_NAME, messageError);
            return;
        }

        AccountStudent.connect(student);
        SceneSwitcher.switchToScene(actionEvent, "DailyView.fxml");
    }
}
