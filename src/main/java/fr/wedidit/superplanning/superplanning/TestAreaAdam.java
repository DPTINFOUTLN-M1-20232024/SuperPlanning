package fr.wedidit.superplanning.superplanning;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.ternary.SessionInstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.others.SessionConnection;
import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TestAreaAdam {

    // https://github.com/MasterDID2022/sopra-project-LHD/blob/main/ressources/db_lhd.sql
    // https://sonarcloud.io/project/issues?id=wedidtln_sopra-project-LHD&branch=main&resolved=false&types=CODE_SMELL
    // https://github.com/marketplace/actions/setup-maven-action
    // https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven
    // https://github.com/brettwooldridge/HikariCP
    // https://www.baeldung.com/thread-pool-java-and-guava
    // https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java

    public static void main(String[] args) {
        loadProperties();

        testGetSessionsFromStudent();
        testTernaryDDB();
        testConnection();

        StudentConnection studentConnection = StudentConnection.of("adam", "lol");
        log.info(studentConnection.getHashPassword());
    }

    public static void loadProperties() {
        try {
            PropertyLoader.loadPropertyFile("app.properties");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static void testGetSessionsFromStudent() {

        Student student;
        try (StudentDAO studentDAO = new StudentDAO()) {
            student = studentDAO.find(1).orElseThrow(() -> new IdentifiableNotFoundException(1));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return;
        }
        log.info(student.toString());
        Set<Session> sessions = new HashSet<>();

        // numero de pagination qu'on doit traduire en semaine

        try {
            sessions = Session.getSessionsFromStudent(student, "2020-01-01", "2020-01-01");
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        } catch (ParseException parseException) {
            log.error(parseException.getLocalizedMessage());
        }

        sessions.forEach(session -> log.info(session.toString()));
    }

    public static void testTernaryDDB() {

        Instructor instructor;
        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            instructor = instructorDAO.find(1).orElseThrow(() -> new IdentifiableNotFoundException(1));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return;
        }

        try {
            SessionInstructorDAO sessionInstructorDAO = new SessionInstructorDAO();
            Set<Session> sessionsInstructor = sessionInstructorDAO.fromE2(instructor);
            sessionsInstructor.forEach(session -> log.info(session.toString()));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        }
    }

    public static void testConnection() {

        // Loading the grade
        long idGrade = 2;
        Grade grade;
        try (GradeDAO gradeDAO = new GradeDAO()) {
            grade = gradeDAO.find(idGrade).orElseThrow(() -> new IdentifiableNotFoundException(idGrade));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return;
        }


        // Création de l'étudiant dans la table "Student" et "SessionConnection"
        Student alexandre = Student.of("alexandre", "lerousseau", grade);
        SessionConnection sessionConnection = SessionConnection.of("alexandre.lerousseau@gmail.com", "azerty");
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            alexandre = studentConnectionDAO.persist(sessionConnection, alexandre);

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        }

        // Récupération de l'étudiant par couple mail/mot de passe
        Student alexandre1;
        alexandre1 = studentConnectionDAO.getStudentFromConnection(sessionConnection2);

        StudentConnection studentConnection2 = StudentConnection.of("alexandre.lerousseau@gmail.com", "azerty");
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            alexandre1 = studentConnectionDAO.getStudentFromConnection(studentConnection2);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return;
        }

        if (alexandre1 == null) {
            log.info("couple mail/mot de passe incorrect");
        } else {
            log.info("success");
        }
    }

}
