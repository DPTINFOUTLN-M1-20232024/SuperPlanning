package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.account.SessionConnection;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Grade;
import fr.wedidit.superplanning.superplanning.utils.RandomPassword;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.mail.MailSender;
import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import fr.wedidit.superplanning.superplanning.utils.views.AutoCompleteBox;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;

public class SecretaryStudentController {

    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private ComboBox<String> comboBoxGrade;
    @FXML
    private TextField textFieldEmail;

    @FXML
    public void initialize() {
        try (GradeDAO gradeDAO = new GradeDAO()) {
            new AutoCompleteBox(comboBoxGrade, gradeDAO);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
        }
    }

    @FXML
    public void onAddStudent(ActionEvent actionEvent) {
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldLastName, "Veuillez renseigner un nom de famille");
            ControllerValidator.textFieldIsNotEmpty(textFieldFirstName, "Veuillez renseigner un prénom");
            ControllerValidator.textFieldIsNotEmpty(textFieldEmail, "Veuillez renseigner un Email");
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        String lastName = textFieldLastName.getText();
        String firstName = textFieldFirstName.getText();
        String email = textFieldEmail.getText();

        String password = RandomPassword.generate();

        Grade grade;
        long moduleId = AutoCompleteBox.getIdFromSearchBar(comboBoxGrade);
        try (GradeDAO gradeDAO = new GradeDAO()){
            grade = gradeDAO.find(moduleId).orElseThrow(() -> new IdentifiableNotFoundException(moduleId));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Student student = Student.of(lastName,
                firstName,
                grade);
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.persist(student);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        SessionConnection sessionConnection = SessionConnection.of(email, password);
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            studentConnectionDAO.persist(sessionConnection, student);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        String accountCreationText = FileUtils.getResourceFileContent("mail", "passwordAccountCreation.txt");
        accountCreationText = fillInfosEmail(accountCreationText, password);
        try {
            MailSender.send("Bienvenue sur votre espace HyperPlanning",
                    accountCreationText,
                    email
            );
        } catch (MessagingException messagingException) {
            Popup.error(messagingException.getLocalizedMessage());
            return;
        }

        textFieldLastName.clear();
        textFieldFirstName.clear();
        textFieldEmail.clear();
        Popup.popup("Succès", "Ajout de l'étudiant avec succès !");
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_MANAGEMENT);
    }

    private String fillInfosEmail(String emailText, String password) {
        return emailText
                .replace("{user.lastname}", textFieldLastName.getText())
                .replace("{user.firstname}", textFieldFirstName.getText())
                .replace("{user.email.name}", textFieldEmail.getText())
                .replace("{user.password}", password)
                .replace("{support.email}", System.getProperty("support.email"));
    }

}
