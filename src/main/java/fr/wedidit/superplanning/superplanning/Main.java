package fr.wedidit.superplanning.superplanning;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.SessionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Main {

    // https://github.com/MasterDID2022/sopra-project-LHD/blob/main/ressources/db_lhd.sql
    // https://sonarcloud.io/project/issues?id=wedidtln_sopra-project-LHD&branch=main&resolved=false&types=CODE_SMELL
    // https://github.com/marketplace/actions/setup-maven-action
    // https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven
    // https://github.com/brettwooldridge/HikariCP
    // https://www.baeldung.com/thread-pool-java-and-guava
    // https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java

    public static void main(String[] args) {
        try {
            PropertyLoader.loadPropertyFile("app.properties");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return;
        }

        Student student;
        try (StudentDAO studentDAO = new StudentDAO()) {
            student = studentDAO.find(1).orElseThrow(() -> new IdentifiableNotFoundException(1));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return;
        }
        log.info(student.toString());

        Set<Module> modules = new HashSet<>();
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            modules = moduleDAO.getModulesFromGrade(student.getGrade());
            modules.forEach(module -> log.info(module.toString()));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        }

        Set<Session> sessions = new HashSet<>();
        try (SessionDAO sessionDAO = new SessionDAO()) {
            for (Module module : modules) {
                sessions.addAll(sessionDAO.getSessionsFromModule(module));
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        }

        sessions.forEach(session -> log.info(session.toString()));

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-01-01 13:30:00.0");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-01-01 14:30:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());
        log.info(start.toString());
        sessions = new HashSet<>();
        try (SessionDAO sessionDAO = new SessionDAO()) {
            for (Module module : modules) {
                sessions.addAll(sessionDAO.getSessionsFromModuleBetween(module, start, end));
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
        }

        sessions.forEach(session -> log.info(session.toString()));

    }

}
