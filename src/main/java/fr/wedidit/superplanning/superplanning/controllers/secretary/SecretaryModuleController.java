package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.ModuleDAO;
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
        String moduleName = moduleNameText.getText();
        long gradeId;
        try {
            gradeId = Long.parseLong(gradeIdText.getText());
        } catch (NumberFormatException numberFormatException) {
            Popup.error("Veuillez entrer un id de promotion correcte (nombre entier).");
            return;
        }

        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            moduleDAO.fromVariables(moduleName, gradeId);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Popup.popup("Succès", "Ajout du module avec succès !");

    }
}
