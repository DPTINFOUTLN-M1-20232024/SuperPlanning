package fr.wedidit.superplanning.superplanning.controllers;

import fr.wedidit.superplanning.superplanning.account.Account;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.utils.gui.AbstractSessionGUI;
import fr.wedidit.superplanning.superplanning.utils.gui.SessionWeekGUI;
import fr.wedidit.superplanning.superplanning.utils.others.TimeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Slf4j
public class WeekViewController {
    /* MODEL PART */

    private Set<SessionWeekGUI> sessionGUISet = new HashSet<>();

    /* GUI PART */
    @FXML
    private AnchorPane edtFrame;
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

    @FXML
    private void initialize() {
        weekPagination.setPageFactory(pageIndex -> {
            sessionGUISet.forEach(AbstractSessionGUI::clear);
            Timestamp[] currentWeekDelimitation = TimeUtils.getCurrentWeekDelimitation(pageIndex);

            initDaysLabels(currentWeekDelimitation[0]);
            try {
                showWeekSessions(currentWeekDelimitation[0], currentWeekDelimitation[1]);
            } catch (ParseException parseException) {
                log.error(parseException.getLocalizedMessage());
            }
            return new Label("Content for page " + pageIndex);
        });

        int weekNumber = TimeUtils.getPaginationIndexFromCurrentDate();
        weekPagination.setPageCount(52);
        weekPagination.setCurrentPageIndex((weekNumber - 33) % 53);

        initStudentInfos();

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
        sessionsWeek.forEach(session -> sessionGUISet.add(SessionWeekGUI.of(edtFrame, session)));
    }

}