package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Instructor;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryInstructorController {

    @FXML
    private TextField firstNameInstructorText;
    @FXML
    private TextField lastNameInstructorText;


    public void onAddInstructor(ActionEvent actionEvent) {
        String firstNameInstructor = firstNameInstructorText.getText();
        String lastNameInstructor = lastNameInstructorText.getText();
        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            instructorDAO.persist(Instructor.of(firstNameInstructor, lastNameInstructor));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }
        Popup.popup("Succès", "Ajout de l'enseignant avec succès !");
    }

    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
    }
}
