package fr.wedidit.superplanning.superplanning.controllers.connections;

import fr.wedidit.superplanning.superplanning.account.AccountSecretary;
import fr.wedidit.superplanning.superplanning.account.AccountStudent;
import fr.wedidit.superplanning.superplanning.account.SessionConnection;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.utils.RandomPassword;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.mail.MailSender;
import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Slf4j
public class ConnectionController {

    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldMail;

    @FXML
    public void onButtonConnectClick(ActionEvent actionEvent) {
        tryLogin(actionEvent);
    }

    @FXML
    public void onEnter(ActionEvent actionEvent) {
        tryLogin(actionEvent);
    }

    @FXML
    private void tryLogin(ActionEvent actionEvent) {
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldMail, "Veuillez préciser le mail");
            ControllerValidator.textFieldIsNotEmpty(textFieldPassword, "Veuillez préciser le mot de passe");
        } catch (ControllerValidatorException e) {
            Popup.error(e.getLocalizedMessage());
            return;
        }

        String mail = textFieldMail.getText();
        String password = textFieldPassword.getText();

        SessionConnection sessionConnection = SessionConnection.of(mail, password);

        AccountStudent.connect(sessionConnection);
        if (AccountStudent.isConnected()) {
            SceneSwitcher.switchToScene(actionEvent, Views.DAILY_VIEW);
            return;
        }

        AccountSecretary.connect(sessionConnection);
        if (AccountSecretary.isConnected()) {
            SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_MANAGEMENT);
            return;
        }

        Popup.error("Mail ou mot de passe éronné.");
    }

    @FXML
    public void onButtonPasswordForget(ActionEvent actionEvent) {
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldMail, "Veuillez écrire un email");
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        String mail = textFieldMail.getText();

        Popup.info("Si un utilisateur avec le mail \"%s\" existe, un nouveau mot de passe lui a été envoyé"
                .formatted(mail));

        Student student;
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            student = studentConnectionDAO.getStudentFromMail(mail);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        log.info("student: %s".formatted(String.valueOf(student)));
        if (student == null) return;

        String password = RandomPassword.generate();
        SessionConnection sessionConnection = SessionConnection.of(mail, password);
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            studentConnectionDAO.update(sessionConnection.getHashPassword(), student.getId());
            log.info("mot de passe modifié");
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        String passwordModificationText = FileUtils.getResourceFileContent("mail", "passwordAccountForget.txt");
        passwordModificationText = fillInfosEmail(passwordModificationText, student, password);
        log.info(passwordModificationText);
        try {
            MailSender.send("Changement de mot de passe sur votre espace HyperPlanning",
                    passwordModificationText,
                    mail
            );
        } catch (MessagingException messagingException) {
            Popup.error(messagingException.getLocalizedMessage());
        }

    }

    private String fillInfosEmail(String emailText, Student student, String password) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return emailText
                .replace("{user.lastname}", student.getLastname())
                .replace("{user.firstname}", student.getFirstname())
                .replace("{user.email.name}", textFieldMail.getText())
                .replace("{user.password}", password)
                .replace("{support.email}", System.getProperty("support.email"))
                .replace("{datetime}", localDateTime.toString());
    }

}
