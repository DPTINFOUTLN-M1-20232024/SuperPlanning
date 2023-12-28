package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Instructor;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryInstructorController {

    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;

    @FXML
    public void onAddInstructor(ActionEvent actionEvent) {
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldFirstName, "Le prénom doit être renseigné");
            ControllerValidator.textFieldIsNotEmpty(textFieldLastName, "Le nom de famille doit être renseigné");
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        String firstNameInstructor = textFieldFirstName.getText();
        String lastNameInstructor = textFieldLastName.getText();

        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            instructorDAO.persist(Instructor.of(firstNameInstructor, lastNameInstructor));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Popup.popup("Succès", "Ajout de l'enseignant avec succès !");
        textFieldFirstName.clear();
        textFieldLastName.clear();
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_MANAGEMENT);
    }
}
