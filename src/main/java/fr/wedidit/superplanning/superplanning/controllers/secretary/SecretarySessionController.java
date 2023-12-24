package fr.wedidit.superplanning.superplanning.controllers.secretary;

import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.concretes.RoomDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.SessionDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.simples.concretes.RoomSimpleDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.simples.others.ModuleSimpleDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.concretes.Room;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.SessionType;
import fr.wedidit.superplanning.superplanning.utils.others.TimeUtils;
import fr.wedidit.superplanning.superplanning.utils.views.AutoCompleteBox;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class SecretarySessionController {
    @FXML
    private TextField textFieldStartTime;
    @FXML
    public TextField textFieldEndTime;
    @FXML
    public DatePicker datePickerDate;
    @FXML
    private ComboBox<String> comboBoxInstructor;
    @FXML
    private ComboBox<String> comboBoxModule;
    @FXML
    private ComboBox<String> comboBoxSessionType;
    @FXML
    private ComboBox<String> comboBoxRoom;

    @FXML
    private void initialize() {
        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            new AutoCompleteBox(comboBoxInstructor, instructorDAO);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        try (ModuleSimpleDAO moduleDAO = new ModuleSimpleDAO()) {
            new AutoCompleteBox(comboBoxModule, moduleDAO);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        try (RoomSimpleDAO roomSimpleDAO = new RoomSimpleDAO()) {
            new AutoCompleteBox(comboBoxRoom, roomSimpleDAO);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        List<String> sessionsType = Stream.of(SessionType.values()).map(SessionType::toString).toList();
        comboBoxSessionType.setItems(FXCollections.observableList(sessionsType));
    }

    @FXML
    public void onAddSession(ActionEvent actionEvent) {
        // TODO verifier:
        //      - si la salle est disponible à la date demandée
        //      - si le prof n'a pas déjà cours à la date demandée
        //      - si la promo n'a pas déjà cours à la date demandée
        Timestamp dateTimeStartSession = parseTime(datePickerDate, textFieldStartTime);
        Timestamp dateTimeEndSession = parseTime(datePickerDate, textFieldEndTime);

        Module module;
        long moduleId = AutoCompleteBox.getIdFromSearchBar(comboBoxModule);
        try (ModuleDAO moduleDAO = new ModuleDAO()){
            module = moduleDAO.find(moduleId).orElseThrow(() -> new IdentifiableNotFoundException(moduleId));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Instructor instructor;
        long instructorId = AutoCompleteBox.getIdFromSearchBar(comboBoxInstructor);
        try (InstructorDAO instructorDAO = new InstructorDAO()){
            instructor = instructorDAO.find(moduleId).orElseThrow(() -> new IdentifiableNotFoundException(instructorId));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Room room;
        long roomId = AutoCompleteBox.getIdFromSearchBar(comboBoxRoom);
        try (RoomDAO roomDAO = new RoomDAO()){
            room = roomDAO.find(roomId).orElseThrow(() -> new IdentifiableNotFoundException(roomId));
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        SessionType sessionType = SessionType.valueOf(comboBoxSessionType.getValue());

        Session session = Session.of(dateTimeStartSession,
                dateTimeEndSession,
                module,
                instructor,
                room,
                sessionType
        );

        try (SessionDAO sessionDAO = new SessionDAO()) {
            sessionDAO.persist(session);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        Popup.popup("Succès", "Cours ajouté avec succès");

    }

    private Timestamp parseTime(DatePicker datePicker, TextField textField) {
        int[] time;
        try {
            time = TimeUtils.parseHoursMinutes(textField.getText());
        } catch (NumberFormatException numberFormatException) {
            Popup.error("Le format de la date de cours n'est pas correct.\nFormat: 14h30 ou 14:30");
            return null;
        }

        return Timestamp.valueOf(datePicker.getValue().atTime(time[0], time[1]));
    }

    public void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchToScene(actionEvent, "SecretaryManagement.fxml");
    }
}
