package fr.wedidit.superplanning.superplanning.database.dao.daolist.ternary;

import fr.wedidit.superplanning.superplanning.database.dao.DAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.TernaryDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.InstructorDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.SessionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionInstructorDAO extends TernaryDAO<Session, Instructor> {

    public SessionInstructorDAO() throws DataAccessException {
        super(TableType.SESSION_INSTRUCTOR, TableType.SESSION, TableType.INSTRUCTOR);
    }

    @Override
    protected DAO<Session> getE1DAO() throws DataAccessException {
        return new SessionDAO();
    }

    @Override
    protected DAO<Instructor> getE2DAO() throws DataAccessException {
        return new InstructorDAO();
    }

}
