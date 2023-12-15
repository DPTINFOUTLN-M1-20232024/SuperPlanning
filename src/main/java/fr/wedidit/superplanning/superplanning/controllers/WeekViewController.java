package fr.wedidit.superplanning.superplanning.controllers;

import fr.wedidit.superplanning.superplanning.account.Account;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.utils.gui.SessionWeekGUI;
import fr.wedidit.superplanning.superplanning.utils.others.TimeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.*;

@Setter
@Slf4j
public class WeekViewController {
    /* MODEL PART */

    private Set<SessionWeekGUI> sessionGUISet = new HashSet<>();

    /* GUI PART */
    /* User information */
    @FXML
    private Label nameUser;
    @FXML
    private Label firstNameUser;
    @FXML
    private Label promotionUser;
    /* WeekView Planning information */
    @FXML
    private Pagination weekPagination;
    @FXML
    private Label mondayDate;
    @FXML
    private Label tuesdayDate;
    @FXML
    private Label wednesdayDate;
    @FXML
    private Label thursdayDate;
    @FXML
    private Label fridayDate;

    public WeekViewController() {
        Timestamp[] currentWeekDelimitation = TimeUtils.getCurrentWeekDelimitation();
        initStudentInfos();
        initDaysLabels(currentWeekDelimitation[0]);
        try {
            showWeekSessions(currentWeekDelimitation[0], currentWeekDelimitation[1]);
        } catch (ParseException parseException) {
            log.error(parseException.getLocalizedMessage());
        }
    }

    private void initDaysLabels(Timestamp beginWeek) {
        LocalDateTime dateOfWeek = beginWeek.toLocalDateTime();
        Label[] daysOfWeek = {mondayDate, tuesdayDate, wednesdayDate, thursdayDate, fridayDate};
        String[] daysName = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
        for (int i = 0; i < 5; i++) {
            Label dayOfWeek = daysOfWeek[i];
            String dayName = daysName[i];
            Timestamp dateOfWeekTimestamp = Timestamp.valueOf(dateOfWeek);
            dayOfWeek.setText("%s %s".formatted(dayName, TimeUtils.getDateInfo(dateOfWeekTimestamp, "dd-MM-yyyy")));
            dateOfWeek = dateOfWeek.plusDays(1);
        }
    }

    private void initStudentInfos() {
        Student student = Account.getStudentAccount();
        firstNameUser.setText(student.getFirstname());
        nameUser.setText(student.getLastname());
        promotionUser.setText(student.getGrade().getName());
    }

    /* Methods */
    @FXML
    private void switchToDailyView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "DailyView.fxml");
    }


    private void showWeekSessions(Timestamp beginWeek, Timestamp endWeek) throws ParseException {
        Student student = Account.getStudentAccount();
        Set<Session> sessionsWeek = null;
        try {
            sessionsWeek = Session.getSessionsFromStudent(student, beginWeek, endWeek);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        }
        sessionsWeek.forEach(session -> sessionGUISet.add(SessionWeekGUI.of(session)));
    }

    private void displaySessionsByDay(Set<Session> sessionsWeek) {

        Map<DayOfWeek, Long> sessionsCountByDay = new HashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            sessionsCountByDay.put(dayOfWeek, 0L);
        }

        for (Session session : sessionsWeek) {
            DayOfWeek dayOfWeek = session.getBegin().toLocalDateTime().getDayOfWeek();
            sessionsCountByDay.put(dayOfWeek, sessionsCountByDay.get(dayOfWeek) + 1);
            sessionGUISet.add(SessionWeekGUI.of(session));
        }


        mondayDate.setText("Lundi " + sessionsCountByDay.get(DayOfWeek.MONDAY));
        tuesdayDate.setText("Mardi " + sessionsCountByDay.get(DayOfWeek.TUESDAY));
        wednesdayDate.setText("Mercredi " + sessionsCountByDay.get(DayOfWeek.WEDNESDAY));
        thursdayDate.setText("Jeudi " + sessionsCountByDay.get(DayOfWeek.THURSDAY));
        fridayDate.setText("Vendredi " + sessionsCountByDay.get(DayOfWeek.FRIDAY));
    }
}