package fr.wedidit.superplanning.superplanning.database.dao.daolist.humans;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class InstructorDAO extends AbstractDAO<Instructor> {

    public InstructorDAO() throws DataAccessException {
        super(TableType.INSTRUCTOR, new String[] {"FIRSTNAME", "LASTNAME"});
    }

    @Override
    protected Instructor instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String firstname = resultSet.getString("FIRSTNAME");
        String lastname = resultSet.getString("LASTNAME");
        return Instructor.of(id, firstname, lastname);
    }

    @Override
    public Instructor persist(Instructor instructor) throws DataAccessException {
        try {
            psPersist.setString(1, instructor.getFirstname());
            psPersist.setString(2, instructor.getLastname());
        } catch (SQLException sqlException) {
            throw new DataAccessException(InstructorDAO.class,
                    sqlException,
                    instructor,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Instructor instructor) throws DataAccessException {
        try {
            psUpdate.setString(1, instructor.getFirstname());
            psUpdate.setString(2, instructor.getLastname());
            psUpdate.setLong(3, instructor.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(InstructorDAO.class,
                    sqlException,
                    instructor,
                    "Unable to update");
        }
        super.update();
    }
}
