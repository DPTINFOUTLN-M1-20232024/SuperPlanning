package fr.wedidit.superplanning.superplanning.controllers.connections;

import fr.wedidit.superplanning.superplanning.account.AccountSecretary;
import fr.wedidit.superplanning.superplanning.account.AccountStudent;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.SecretaryConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.account.SessionConnection;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionController {

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
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        // Check if mail and password is correct
        if (student == null) {

            try (SecretaryConnectionDAO secretaryConnectionDAO = new SecretaryConnectionDAO()) {
                if (secretaryConnectionDAO.getSecretaryFromConnection(sessionConnection)) {
                    AccountSecretary.logging();
                    SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
                    return;
                }
            } catch (DataAccessException dataAccessException) {
                Popup.error(dataAccessException.getLocalizedMessage());
            }

            Popup.error("Mail or password is not correct");
            return;
        }

        AccountStudent.connect(student);
        SceneSwitcher.switchToScene(actionEvent, "DailyView.fxml");
    }
}
