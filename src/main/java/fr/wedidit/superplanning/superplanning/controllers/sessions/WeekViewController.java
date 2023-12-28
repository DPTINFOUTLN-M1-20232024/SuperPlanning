package fr.wedidit.superplanning.superplanning.controllers.sessions;

import fr.wedidit.superplanning.superplanning.account.AccountStudent;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Session;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.gui.AbstractSessionGUI;
import fr.wedidit.superplanning.superplanning.utils.gui.SessionWeekGUI;
import fr.wedidit.superplanning.superplanning.utils.others.TimeUtils;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
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
            showWeekSessions(currentWeekDelimitation[0], currentWeekDelimitation[1]);
            return new Label("i");
        });

        int weekNumber = TimeUtils.getPaginationIndexFromCurrentDate();
        weekPagination.setPageCount(52);
        weekPagination.setCurrentPageIndex((weekNumber - 33) % 53);

        initStudentInfos();

    }

    @FXML
    private void onMouseEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #4F7FAA");
    }

    @FXML
    private void onMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color:  #1C3C66");
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
        Student student = AccountStudent.getStudentAccount();
        firstNameUser.setText(student.getFirstname());
        nameUser.setText(student.getLastname());
        promotionUser.setText(student.getGrade().getName());
    }

    /* Methods */
    @FXML
    private void switchToDailyView(ActionEvent event) {
        SceneSwitcher.switchToScene(event, Views.DAILY_VIEW);
    }


    private void showWeekSessions(Timestamp beginWeek, Timestamp endWeek) {
        Student student = AccountStudent.getStudentAccount();
        Set<Session> sessionsWeek;
        try {
            sessionsWeek = Session.getSessionsFromStudent(student, beginWeek, endWeek);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }
        sessionsWeek.forEach(session -> sessionGUISet.add(SessionWeekGUI.of(edtFrame, session)));
    }

}