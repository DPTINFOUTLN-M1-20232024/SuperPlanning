package fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.concretes;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.concretes.Building;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingDAO extends AbstractDAO<Building> {

    public BuildingDAO() throws DataAccessException {
        super(TableType.BUILDING, new String[]{"NAME"});
    }

    @Override
    protected Building instantiateEntity(ResultSet resultSet) throws SQLException {
        return Building.of(resultSet.getLong("ID"), resultSet.getString("NAME"));
    }

    @Override
    public Building persist(Building building) throws DataAccessException {
        try {
            psPersist.setString(1, building.getName());
        } catch (SQLException sqlException) {
            throw new DataAccessException(BuildingDAO.class,
                    sqlException,
                    building,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Building building) throws DataAccessException {
        try {
            psUpdate.setString(1, building.getName());
            psUpdate.setLong(2, building.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(BuildingDAO.class,
                    sqlException,
                    building,
                    "Unable to update");
        }
        super.update();
    }
}
