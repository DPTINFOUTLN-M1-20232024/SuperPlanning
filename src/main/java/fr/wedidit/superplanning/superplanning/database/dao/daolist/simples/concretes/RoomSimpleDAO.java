package fr.wedidit.superplanning.superplanning.database.dao.daolist.simples.concretes;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.OnlyReadAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.simples.concretes.RoomSimple;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomSimpleDAO extends AbstractDAO<RoomSimple> {


    public RoomSimpleDAO() throws DataAccessException {
        super(TableType.ROOM, new String[] {"NAME"});
    }

    @Override
    protected RoomSimple instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String name = resultSet.getString("NAME");
        return RoomSimple.of(id, name);
    }

    @Override
    public RoomSimple persist(RoomSimple roomSimple) throws DataAccessException {
        throw new OnlyReadAccessException();
    }

    @Override
    public void update(RoomSimple roomSimple) throws DataAccessException {
        throw new OnlyReadAccessException();
    }
}
