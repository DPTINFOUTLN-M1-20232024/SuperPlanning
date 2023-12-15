package fr.wedidit.superplanning.superplanning.identifiables.others;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.SessionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Room;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@Slf4j
public class Session implements Identifiable {

    private final long id;
    private final Timestamp begin;
    private final Timestamp finish;
    private final Module module;
    private final Instructor instructor;
    private final Room room;
    private final SessionType sessionType;

    private Session(long id, Timestamp begin, Timestamp finish, Module module, Instructor instructor, Room room, SessionType sessionType) {
        this.id = id;
        this.begin = begin;
        this.finish = finish;
        this.module = module;
        this.instructor = instructor;
        this.room = room;
        this.sessionType = sessionType;
    }

    public static Session of(long id, Timestamp begin, Timestamp finish, Module module, Instructor instructor, Room room, SessionType sessionType) {
        return new Session(id, begin, finish, module, instructor, room, sessionType);
    }

    public static Set<Session> getSessionsFromStudent(Student student, String beginDay, String endDay) throws DataAccessException, ParseException{

        Set<Module> modules;
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            modules = moduleDAO.getModulesFromGrade(student.getGrade());
        }

        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("%s 00:00:01".formatted(beginDay));
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("%s 23:59:59".formatted(endDay));
        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());

        Set<Session> sessions = new HashSet<>();
        try (SessionDAO sessionDAO = new SessionDAO()){
            for (Module module : modules) {
                sessions.addAll(sessionDAO.getSessionsFromModuleBetween(module, start, end));
            }
        }

        return sessions;
    }

}
