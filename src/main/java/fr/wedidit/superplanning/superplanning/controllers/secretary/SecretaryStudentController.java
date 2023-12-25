package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Grade;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.views.AutoCompleteBox;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SecretaryStudentController {

    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private ComboBox<String> comboBoxGrade;

    @FXML
    public void initialize() {
        try (GradeDAO gradeDAO = new GradeDAO()) {
            new AutoCompleteBox(comboBoxGrade, gradeDAO);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }
    }

    public void onAddStudent(ActionEvent actionEvent) {
        String lastName = textFieldLastName.getText();
        String firstName = textFieldFirstName.getText();

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

        Popup.popup("Succès", "Ajout de l'étudiant avec succès !");
    }

    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
    }

}
