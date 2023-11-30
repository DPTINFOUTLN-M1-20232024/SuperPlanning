package fr.wedidit.superplanning.superplanning.database.dao.daolist.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeDAO extends AbstractDAO<Grade> {

    public GradeDAO() throws DataAccessException {
        super(TableType.GRADE, new String[] {"NAME"});
    }

    @Override
    protected Grade instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String name = resultSet.getString("NAME");
        return Grade.of(id, name);
    }

    @Override
    public Grade persist(Grade grade) throws DataAccessException {
        try {
            psPersist.setString(1, grade.getName());
        } catch (SQLException sqlException) {
            throw new DataAccessException(GradeDAO.class,
                    sqlException,
                    grade,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Grade grade) throws DataAccessException {
        try {
            psUpdate.setString(1, grade.getName());
            psUpdate.setLong(2, grade.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(GradeDAO.class,
                    sqlException,
                    grade,
                    "Unable to update");
        }
        super.update();
    }
}
