package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Instructor;
import fr.wedidit.superplanning.superplanning.utils.controllers.validator.ControllerValidatorUtils;
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
        if (firstNameInstructor.isEmpty()) {
            Popup.info("Le prénom de l'enseignant n'a pas été renseigné");
            return;
        }

        String lastNameInstructor = lastNameInstructorText.getText();
        if (lastNameInstructor.isEmpty()) {
            Popup.info("Le nom de famille de l'enseignant n'a pas été renseigné");
            return;
        }

        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            instructorDAO.persist(Instructor.of(firstNameInstructor, lastNameInstructor));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Popup.popup("Succès", "Ajout de l'enseignant avec succès !");
        firstNameInstructorText.clear();
        lastNameInstructorText.clear();
    }

    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
    }
}
