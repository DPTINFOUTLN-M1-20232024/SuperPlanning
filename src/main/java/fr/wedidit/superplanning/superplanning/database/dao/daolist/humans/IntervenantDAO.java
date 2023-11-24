package fr.wedidit.superplanning.superplanning.database.dao.daolist.humans;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Intervenant;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class IntervenantDAO extends AbstractDAO<Intervenant> {

    public IntervenantDAO() throws DataAccessException {
        super(TableType.INTERVENANT, new String[] {"FIRSTNAME", "LASTNAME"});
    }

    @Override
    protected Intervenant instantiateEntity(ResultSet resultSet) throws SQLException {
        
    }

    @Override
    public Intervenant persist(Intervenant intervenant) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Intervenant intervenant) throws DataAccessException {

    }
}
