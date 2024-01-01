package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidator;
import fr.wedidit.superplanning.superplanning.controllers.validators.ControllerValidatorException;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Module;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.utils.views.AutoCompleteBox;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryModuleController {

    @FXML
    private TextField textFieldModuleName;
    @FXML
    private ComboBox<String> comboBoxGrade;

    @FXML
    public void initialize() {
        try (GradeDAO gradeDAO = new GradeDAO()) {
            new AutoCompleteBox(comboBoxGrade, gradeDAO);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
        }
    }

    @FXML
    public void onAddModule(ActionEvent actionEvent) {
        try {
            ControllerValidator.textFieldIsNotEmpty(textFieldModuleName, "Le nom du module ne doit pas être vide");
        } catch (ControllerValidatorException controllerValidatorException) {
            Popup.error(controllerValidatorException.getLocalizedMessage());
            return;
        }

        long gradeId = AutoCompleteBox.getIdFromSearchBar(comboBoxGrade);

        String moduleName = textFieldModuleName.getText();

        Module module;
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            module = moduleDAO.fromVariables(moduleName, gradeId);
        } catch (DataAccessException dataAccessException) {
            Popup.error("Veuillez entrer un identifiant de promotion valide");
            return;
        }

        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            module = moduleDAO.persist(module);
        } catch (DataAccessException dataAccessException) {
            Popup.error("Veuillez entrer un identifiant de promotion valide");
            return;
        }

        Popup.popup("Succès", "Ajout du module avec succès !");
        textFieldModuleName.clear();
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, Views.SECRETARY_MANAGEMENT);
    }
}
