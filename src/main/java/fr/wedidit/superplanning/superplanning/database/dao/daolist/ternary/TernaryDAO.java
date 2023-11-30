package fr.wedidit.superplanning.superplanning.database.dao.daolist.ternary;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public class TernaryDAO {

    private final String tableName;
    protected final Connection connection;

    protected TernaryDAO(Enum<?> tableEnum) throws DataAccessException {
        this.tableName = tableEnum.name();
        try {
            this.connection = DBCPDataSource.getConnection();
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to load TernaryClass");
        }
    }

}
