package fr.wedidit.superplanning.superplanning.database.dao.daolist.concretes;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Batiment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BatimentDAO extends AbstractDAO<Batiment> {

    public BatimentDAO() throws DataAccessException {
        super(TableType.BATIMENT, new String[]{"NAME"});
    }

    @Override
    protected Batiment instantiateEntity(ResultSet resultSet) throws SQLException {
        return Batiment.of(resultSet.getLong("ID"), resultSet.getString("NAME"));
    }

    @Override
    public Batiment persist(Batiment batiment) throws DataAccessException {
        try {
            psPersist.setString(1, batiment.getName());
        } catch (SQLException sqlException) {
            throw new DataAccessException(BatimentDAO.class,
                    sqlException,
                    batiment,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Batiment batiment) throws DataAccessException {
        try {
            psUpdate.setString(1, batiment.getName());
            psUpdate.setLong(2, batiment.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(BatimentDAO.class,
                    sqlException,
                    batiment,
                    "Unable to update");
        }
        super.update();
    }
}
