package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Module;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryModuleController {

    @FXML
    private TextField textFieldModuleName;
    @FXML
    private TextField textFieldGradeId;

    @FXML
    public void onAddModule(ActionEvent actionEvent) {
        long gradeId;
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldModuleName, "Le nom du module ne doit pas être vide");
            gradeId = ControllerValidator.getId(textFieldGradeId);
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        String moduleName = textFieldModuleName.getText();

        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            Module module = moduleDAO.fromVariables(moduleName, gradeId);
            moduleDAO.persist(module);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Popup.popup("Succès", "Ajout du module avec succès !");
        textFieldModuleName.clear();
        textFieldGradeId.clear();
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_MANAGEMENT);
    }
}
