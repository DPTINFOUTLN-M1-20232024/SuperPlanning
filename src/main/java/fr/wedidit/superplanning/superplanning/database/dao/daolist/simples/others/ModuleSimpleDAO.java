package fr.wedidit.superplanning.superplanning.database.dao.daolist.simples.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.OnlyReadAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.simples.others.ModuleSimple;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleSimpleDAO extends AbstractDAO<ModuleSimple> {
    public ModuleSimpleDAO() throws DataAccessException {
        super(TableType.MODULE, new String[] {"NAME"});
    }

    @Override
    protected ModuleSimple instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String name = resultSet.getString("NAME");
        return ModuleSimple.of(id, name);
    }

    @Override
    public ModuleSimple persist(ModuleSimple moduleSimple) throws DataAccessException {
        throw new OnlyReadAccessException();
    }

    @Override
    public void update(ModuleSimple moduleSimple) throws DataAccessException {
        throw new OnlyReadAccessException();
    }
}
