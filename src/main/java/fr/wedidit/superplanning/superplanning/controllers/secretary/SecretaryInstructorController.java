package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
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
            log.error(dataAccessException.getLocalizedMessage());
            Popup.popup("Erreur", dataAccessException.getLocalizedMessage());
            return;
        }
        Popup.popup("Succès", "Ajout de l'enseignant avec succès !");
    }
}
