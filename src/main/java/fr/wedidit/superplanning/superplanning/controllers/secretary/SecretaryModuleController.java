package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.controllers.exceptions.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.exceptions.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryModuleController {

    @FXML
    private TextField moduleNameText;
    @FXML
    private TextField gradeIdText;

    public void onAddModule(ActionEvent actionEvent) {
        long gradeId;
        try {
            ControllerValidator.textFieldIsNotEmpty(moduleNameText);
            gradeId = ControllerValidator.getId(gradeIdText);
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        String moduleName = moduleNameText.getText();

        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.fromVariables(moduleName, gradeId);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Popup.popup("Succès", "Ajout du module avec succès !");
        moduleNameText.clear();
        gradeIdText.clear();
    }

    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
    }
}
