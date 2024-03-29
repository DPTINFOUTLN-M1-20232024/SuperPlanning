package fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.concretes.RoomDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.concretes.Room;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.completes.others.SessionType;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SessionDAO extends AbstractDAO<Session> {

    private final PreparedStatement psFindAllSessionFromModule;
    private final PreparedStatement psFindAllSessionFromModuleBetween;

    public SessionDAO() throws DataAccessException {
        super(TableType.SESSION, SessionColumns.class);


        String requestFindAllSessionFromModule = """
                        SELECT SESSION.ID, SESSION.BEGIN, SESSION.FINISH, SESSION.ID_INSTRUCTOR, SESSION.ID_ROOM, SESSION.SESSION_TYPE
                        FROM SESSION
                        WHERE SESSION.ID_MODULE = ?;
                        """;

        String requestFindAllSessionFromModuleBetween = """
                        SELECT SESSION.ID, SESSION.BEGIN, SESSION.FINISH, SESSION.ID_INSTRUCTOR, SESSION.ID_ROOM, SESSION.SESSION_TYPE
                        FROM SESSION
                        WHERE SESSION.ID_MODULE = ?
                        AND ? <= SESSION.BEGIN
                        AND SESSION.FINISH <= ?;
                        """;

        try {
            psFindAllSessionFromModule = connection.prepareStatement(requestFindAllSessionFromModule);
            psFindAllSessionFromModuleBetween = connection.prepareStatement(requestFindAllSessionFromModuleBetween);
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    "Unable create prepared statement in SessionDAO constructor");
        }
    }

    public Set<Session> getSessionsFromModule(Module module) throws DataAccessException {
        Set<Session> sessions = new HashSet<>();

        try {
            psFindAllSessionFromModule.setLong(1, module.getId());
            getSessions(module, sessions, psFindAllSessionFromModule);
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    "Unable to search the session from module");
        }

        return sessions;
    }

    public Set<Session> getSessionsFromModuleBetween(Module module, Timestamp beginPossible, Timestamp finishPossible) throws DataAccessException {
        Set<Session> sessions = new HashSet<>();

        try {
            psFindAllSessionFromModuleBetween.setLong(1, module.getId());
            psFindAllSessionFromModuleBetween.setTimestamp(2, beginPossible);
            psFindAllSessionFromModuleBetween.setTimestamp(3, finishPossible);
            getSessions(module, sessions, psFindAllSessionFromModuleBetween);
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    "Unable to search the session from module between");
        }

        return sessions;
    }

    private void getSessions(Module module, Set<Session> sessions, PreparedStatement psFindAllSessionFromModuleBetween) throws SQLException, DataAccessException {
        ResultSet rsFindAllSessionFromModuleBetween = psFindAllSessionFromModuleBetween.executeQuery();
        Session session;
        while (rsFindAllSessionFromModuleBetween.next()) {
            long id = rsFindAllSessionFromModuleBetween.getLong("ID");
            Timestamp begin = rsFindAllSessionFromModuleBetween.getTimestamp(SessionColumns.BEGIN.name());
            Timestamp finish = rsFindAllSessionFromModuleBetween.getTimestamp(SessionColumns.FINISH.name());

            long idInstructor = rsFindAllSessionFromModuleBetween.getLong(SessionColumns.ID_INSTRUCTOR.name());
            Instructor instructor;
            try (InstructorDAO instructorDAO = new InstructorDAO()) {
                instructor = instructorDAO.find(idInstructor).orElseThrow(() -> new IdentifiableNotFoundException(idInstructor));
            }

            long idRoom = rsFindAllSessionFromModuleBetween.getLong(SessionColumns.ID_ROOM.name());
            Room room;
            try (RoomDAO roomDAO = new RoomDAO()) {
                room = roomDAO.find(idRoom).orElseThrow(() -> new IdentifiableNotFoundException(idRoom));
            }

            String sessionTypeString = rsFindAllSessionFromModuleBetween.getString(SessionColumns.SESSION_TYPE.name());
            SessionType sessionType = SessionType.valueOf(sessionTypeString);

            session = Session.of(id, begin, finish, module, instructor, room, sessionType);
            sessions.add(session);
        }
    }

    public Session fromVariables(long id, Timestamp start, Timestamp end, long idModule, long idInstructor, long idRoom, SessionType sessionType) {
        Module module;
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            module = moduleDAO.find(idModule).orElseThrow(() -> new IdentifiableNotFoundException(idModule));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        Instructor instructor;
        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            instructor = instructorDAO.find(idInstructor).orElseThrow(() -> new IdentifiableNotFoundException(idInstructor));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        Room room;
        try (RoomDAO roomDAO = new RoomDAO()) {
            room = roomDAO.find(idRoom).orElseThrow(() -> new IdentifiableNotFoundException(idRoom));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        return Session.of(id,
                start,
                end,
                module,
                instructor,
                room,
                sessionType);
    }

    public Session fromVariables(Timestamp start, Timestamp end, long idModule, long idInstructor, long idRoom, SessionType sessionType) {
        return fromVariables(-1, start, end, idModule, idInstructor, idRoom, sessionType);
    }

    @Override
    protected Session instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        Timestamp start = resultSet.getTimestamp(SessionColumns.BEGIN.name());
        Timestamp end = resultSet.getTimestamp(SessionColumns.FINISH.name());
        long idModule = resultSet.getLong(SessionColumns.ID_MODULE.name());
        long idInstructor = resultSet.getLong(SessionColumns.ID_INSTRUCTOR.name());
        long idRoom = resultSet.getLong(SessionColumns.ID_ROOM.name());
        String sessionTypeString = resultSet.getString(SessionColumns.SESSION_TYPE.name());
        SessionType sessionType = SessionType.valueOf(sessionTypeString);
        return fromVariables(id, start, end, idModule, idInstructor, idRoom, sessionType);
    }

    @Override
    public Session persist(Session session) throws DataAccessException {
        try {
            psPersist.setTimestamp(SessionColumns.BEGIN.colNumber(), session.getBegin());
            psPersist.setTimestamp(SessionColumns.FINISH.colNumber(), session.getFinish());
            psPersist.setLong(SessionColumns.ID_MODULE.colNumber(), session.getModule().getId());
            psPersist.setLong(SessionColumns.ID_INSTRUCTOR.colNumber(), session.getInstructor().getId());
            psPersist.setLong(SessionColumns.ID_ROOM.colNumber(), session.getRoom().getId());
            psPersist.setString(SessionColumns.SESSION_TYPE.colNumber(), session.getSessionType().name());
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    session,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Session session) throws DataAccessException {
        try {
            psUpdate.setTimestamp(SessionColumns.BEGIN.colNumber(), session.getBegin());
            psUpdate.setTimestamp(SessionColumns.FINISH.colNumber(), session.getFinish());
            psUpdate.setLong(SessionColumns.ID_MODULE.colNumber(), session.getModule().getId());
            psUpdate.setLong(SessionColumns.ID_INSTRUCTOR.colNumber(), session.getInstructor().getId());
            psUpdate.setLong(SessionColumns.ID_ROOM.colNumber(), session.getRoom().getId());
            psUpdate.setString(SessionColumns.SESSION_TYPE.colNumber(), session.getSessionType().name());
            psUpdate.setLong(7, session.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    session,
                    "Unable to update");
        }
        super.update();
    }

    private enum SessionColumns {
        BEGIN,
        FINISH,
        ID_MODULE,
        ID_INSTRUCTOR,
        ID_ROOM,
        SESSION_TYPE;

        public int colNumber() {
            return ordinal() + 1;
        }
    }

}
