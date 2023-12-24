package fr.wedidit.superplanning.superplanning.controllers.sessions;

import fr.wedidit.superplanning.superplanning.account.AccountStudent;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Session;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.gui.AbstractSessionGUI;
import fr.wedidit.superplanning.superplanning.utils.gui.SessionDailyGUI;
import fr.wedidit.superplanning.superplanning.utils.others.TimeUtils;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class DailyViewController {

    private final Set<SessionDailyGUI> sessionGUISet = new HashSet<>();
    @FXML
    private Label nameUser;
    @FXML
    private Label firstNameUser;
    @FXML
    private Label promotionUser;
    @FXML
    private AnchorPane edtFrame;
    @FXML
    private DatePicker datePicker;

    public void initialize() {
        datePicker.setValue(LocalDate.now());

        Timestamp[] currentDayDelimitation = TimeUtils.getCurrentDayDelimitation();
        showDaySessions(currentDayDelimitation[0], currentDayDelimitation[1]);
    }
    @FXML
    private void nextDay(ActionEvent event) {
        LocalDate currentDate = datePicker.getValue();
        datePicker.setValue(currentDate.plusDays(1));
        loadSessions();
    }

    private void loadSessions() {
        LocalDate selectedDate = datePicker.getValue();
        LocalDateTime localDateTime = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT);
        LocalDateTime startDay = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(0, 0, 1));
        LocalDateTime endDay = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(23, 59, 59));
        Timestamp startDayTimestamp = Timestamp.valueOf(startDay);
        Timestamp endDayTimestamp = Timestamp.valueOf(endDay);
        sessionGUISet.forEach(AbstractSessionGUI::clear);
        showDaySessions(startDayTimestamp, endDayTimestamp);
    }

    @FXML
    private void prevDay(ActionEvent event) {
        LocalDate currentDate = datePicker.getValue();
        datePicker.setValue(currentDate.minusDays(1));
        loadSessions();
    }

    @FXML
    private void switchToWeekView(ActionEvent event) {
        SceneSwitcher.switchToScene(event, "WeekView.fxml");
    }

    @FXML
    private void onMouseEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #7289DA");
    }

    @FXML
    private void onMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #4E4F55");
    }

    @FXML
    private void onButtonDisconnect(ActionEvent actionEvent) {
        AccountStudent.disconnect();
        SceneSwitcher.switchToScene(actionEvent, "Connection.fxml");
    }

    private void showDaySessions(Timestamp startDay, Timestamp endDay) {
        Student student = AccountStudent.getStudentAccount();
        Set<Session> sessionsDay;
        try {
            sessionsDay = Session.getSessionsFromStudent(student, startDay, endDay);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }
        sessionsDay.forEach(session -> sessionGUISet.add(SessionDailyGUI.of(edtFrame, session)));
    }

}
