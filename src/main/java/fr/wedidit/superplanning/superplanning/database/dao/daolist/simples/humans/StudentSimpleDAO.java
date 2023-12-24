package fr.wedidit.superplanning.superplanning.database.dao.daolist.simples.humans;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.OnlyReadAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.simples.humans.StudentSimple;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentSimpleDAO extends AbstractDAO<StudentSimple> {
    public StudentSimpleDAO() throws DataAccessException {
        super(TableType.STUDENT, new String[] {"FIRSTNAME", "LASTNAME"});
    }

    @Override
    protected StudentSimple instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String firstname = resultSet.getString("FIRSTNAME");
        String lastname = resultSet.getString("LASTNAME");
        return StudentSimple.of(id, firstname, lastname);
    }

    @Override
    public StudentSimple persist(StudentSimple studentSimple) throws DataAccessException {
        throw new OnlyReadAccessException();
    }

    @Override
    public void update(StudentSimple studentSimple) throws DataAccessException {
        throw new OnlyReadAccessException();
    }
}
