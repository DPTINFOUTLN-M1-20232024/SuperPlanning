package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import fr.wedidit.superplanning.superplanning.vues.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryGradeController {
    @FXML
    private TextField gradeNameText;

    public void onAddGrade(ActionEvent actionEvent) {
        String gradeName = gradeNameText.getText();
        try (GradeDAO gradeDAO = new GradeDAO()) {
            gradeDAO.persist(Grade.of(gradeName));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            Popup.popup("Erreur", dataAccessException.getLocalizedMessage());
            return;
        }
        Popup.popup("Succès", "Ajout de la promotion avec succès !");
    }

}
