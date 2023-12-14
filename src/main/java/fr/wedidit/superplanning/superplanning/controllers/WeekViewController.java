package fr.wedidit.superplanning.superplanning.controllers;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.SessionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.*;

public class WeekViewController {
    /*DAO utilisation*/
    private SessionDAO sessionDAO;
    private StudentDAO studentDAO;
    private ModuleDAO moduleDAO;

    /* MODEL PART */


    /* GUI PART */
    /* User informations */
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

    /* Methods */
    @FXML
    private void switchToDailyView(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene(event, "DailyView.fxml");
    }


    private void showWeekSessions(long studentId, Timestamp beginPossible, Timestamp finishPossible) throws DataAccessException {
        Optional<Student> st = studentDAO.find(studentId);
        if (st.isEmpty()) {
            throw new IdentifiableNotFoundException(studentId);
        }
        firstNameUser.setText(st.get().getFirstname());
        Grade gr = st.get().getGrade();
        Set<Module> modules = moduleDAO.getModulesFromGrade(gr);
        Set<Session> sessionsWeek = new HashSet<>();//=sessionDAO.getSessionsFromModuleBetween()
        for (var mod : modules) {
            sessionsWeek.addAll(sessionDAO.getSessionsFromModuleBetween(mod, beginPossible, finishPossible));

        }
        displaySessionsByDay(sessionsWeek);


    }

    private void displaySessionsByDay(Set<Session> sessionsWeek) {

        Map<DayOfWeek, Long> sessionsCountByDay;
        sessionsCountByDay = new HashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            sessionsCountByDay.put(dayOfWeek, 0L);
        }

        for (Session session : sessionsWeek) {
            DayOfWeek dayOfWeek = session.getBegin().toLocalDateTime().getDayOfWeek();
            sessionsCountByDay.put(dayOfWeek, sessionsCountByDay.get(dayOfWeek) + 1);
        }


        mondayDate.setText("Monday: " + sessionsCountByDay.get(DayOfWeek.MONDAY));
        tuesdayDate.setText("Tuesday: " + sessionsCountByDay.get(DayOfWeek.TUESDAY));
        wednesdayDate.setText("Wednesday: " + sessionsCountByDay.get(DayOfWeek.WEDNESDAY));
        thursdayDate.setText("Thursday: " + sessionsCountByDay.get(DayOfWeek.THURSDAY));
        fridayDate.setText("Friday: " + sessionsCountByDay.get(DayOfWeek.FRIDAY));
    }
    }
