package fr.wedidit.superplanning.superplanning.database.dao.daolist.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.concretes.RoomDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Room;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
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
        super(TableType.SESSION, new String[] {"BEGIN", "FINISH", "ID_MODULE", "ID_INSTRUCTOR", "ID_ROOM"});


        String requestFindAllSessionFromModule = """
                        SELECT SESSION.ID, SESSION.BEGIN, SESSION.FINISH, SESSION.ID_INSTRUCTOR, SESSION.ID_ROOM
                        FROM SESSION
                        WHERE SESSION.ID_MODULE = ?;
                        """;

        String requestFindAllSessionFromModuleBetween = """
                        SELECT SESSION.ID, SESSION.BEGIN, SESSION.FINISH, SESSION.ID_INSTRUCTOR, SESSION.ID_ROOM
                        FROM SESSION
                        WHERE SESSION.ID_MODULE = ?
                        AND ? <= SESSION.BEGIN
                        AND SESSION.FINISH >= ?;
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
            ResultSet rsFindAllSessionFromModuleBetween = psFindAllSessionFromModule.executeQuery();
            Session session;
            while (rsFindAllSessionFromModuleBetween.next()) {
                long id = rsFindAllSessionFromModuleBetween.getLong("ID");
                Timestamp begin = rsFindAllSessionFromModuleBetween.getTimestamp("BEGIN");
                Timestamp finish = rsFindAllSessionFromModuleBetween.getTimestamp("FINISH");

                long idInstructor = rsFindAllSessionFromModuleBetween.getLong("ID_INSTRUCTOR");
                Instructor instructor = null;
                try (InstructorDAO instructorDAO = new InstructorDAO()) {
                    instructor = instructorDAO.find(idInstructor).orElseThrow(() -> new IdentifiableNotFoundException(idInstructor));
                }

                long idRoom = rsFindAllSessionFromModuleBetween.getLong("ID_ROOM");
                Room room;
                try (RoomDAO roomDAO = new RoomDAO()) {
                    room = roomDAO.find(idRoom).orElseThrow(() -> new IdentifiableNotFoundException(idRoom));
                }

                session = Session.of(id, begin, finish, module, instructor, room);
                sessions.add(session);
            }
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
            ResultSet rsFindAllSessionFromModuleBetween = psFindAllSessionFromModuleBetween.executeQuery();
            Session session;
            while (rsFindAllSessionFromModuleBetween.next()) {
                long id = rsFindAllSessionFromModuleBetween.getLong("ID");
                Timestamp begin = rsFindAllSessionFromModuleBetween.getTimestamp("BEGIN");
                Timestamp finish = rsFindAllSessionFromModuleBetween.getTimestamp("FINISH");

                long idInstructor = rsFindAllSessionFromModuleBetween.getLong("ID_INSTRUCTOR");
                Instructor instructor = null;
                try (InstructorDAO instructorDAO = new InstructorDAO()) {
                    instructor = instructorDAO.find(idInstructor).orElseThrow(() -> new IdentifiableNotFoundException(idInstructor));
                }

                long idRoom = rsFindAllSessionFromModuleBetween.getLong("ID_ROOM");
                Room room;
                try (RoomDAO roomDAO = new RoomDAO()) {
                    room = roomDAO.find(idRoom).orElseThrow(() -> new IdentifiableNotFoundException(idRoom));
                }

                session = Session.of(id, begin, finish, module, instructor, room);
                sessions.add(session);
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    "Unable to search the session from module between");
        }

        return sessions;
    }

    @Override
    protected Session instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        Timestamp start = resultSet.getTimestamp("BEGIN");
        Timestamp end = resultSet.getTimestamp("FINISH");

        Module module;
        long idModule = resultSet.getLong("ID_MODULE");
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            module = moduleDAO.find(idModule).orElseThrow(() -> new IdentifiableNotFoundException(idModule));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        Instructor instructor;
        long idInstructor = resultSet.getLong("ID_INSTRUCTOR");
        try (InstructorDAO instructorDAO = new InstructorDAO()) {
            instructor = instructorDAO.find(idInstructor).orElseThrow(() -> new IdentifiableNotFoundException(idInstructor));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        Room room;
        long idRoom = resultSet.getLong("ID_ROOM");
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
                room);

    }

    @Override
    public Session persist(Session session) throws DataAccessException {
        try {
            psPersist.setTimestamp(1, session.getBegin());
            psPersist.setTimestamp(2, session.getFinish());
            psPersist.setLong(3, session.getModule().getId());
            psPersist.setLong(4, session.getInstructor().getId());
            psPersist.setLong(5, session.getRoom().getId());
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
            psUpdate.setTimestamp(1, session.getBegin());
            psUpdate.setTimestamp(2, session.getFinish());
            psUpdate.setLong(3, session.getModule().getId());
            psUpdate.setLong(4, session.getInstructor().getId());
            psUpdate.setLong(5, session.getRoom().getId());
            psUpdate.setLong(6, session.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(SessionDAO.class,
                    sqlException,
                    session,
                    "Unable to update");
        }
        super.update();
    }

}
