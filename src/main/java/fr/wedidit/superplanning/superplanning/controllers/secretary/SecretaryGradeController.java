package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Grade;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryGradeController {

    @FXML
    private TextField textFieldGradeName;

    @FXML
    public void onAddGrade(ActionEvent actionEvent) {
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldGradeName, "Le nom de la promotion doit être renseigné");
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        String gradeName = textFieldGradeName.getText();
        try (GradeDAO gradeDAO = new GradeDAO()) {
            gradeDAO.persist(Grade.of(gradeName));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }
        Popup.popup("Succès", "Ajout de la promotion avec succès !");
        textFieldGradeName.clear();
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_MANAGEMENT);
    }

}
