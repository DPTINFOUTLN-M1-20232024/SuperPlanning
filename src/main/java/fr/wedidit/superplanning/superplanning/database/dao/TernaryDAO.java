package fr.wedidit.superplanning.superplanning.database.dao;

import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class TernaryDAO<E1 extends Identifiable, E2 extends Identifiable> {

    private final String e1ColumnName;
    private final String e2ColumnName;
    protected final Connection connection;
    private final PreparedStatement psFindE1FromE2;
    private final PreparedStatement psFindE2FromE1;

    protected TernaryDAO(Enum<?> tableEnum, Enum<?> e1TableCol, Enum<?> e2TableName) throws DataAccessException {
        String tableName = tableEnum.name();
        this.e1ColumnName = e1TableCol.name();
        this.e2ColumnName = e2TableName.name();

        // e1 = session

        String requestFindE1FromE2 = """
                SELECT %s.ID_%s
                FROM %s
                WHERE %s.ID_%s = ?;
                """.formatted(tableName, e1ColumnName,
                tableName, tableName, e2ColumnName);
        String requestFindE2FromE1 = """
                SELECT %s.ID_%s
                FROM %s
                WHERE %s.ID_%s = ?;
                """.formatted(tableName, e2ColumnName,
                tableName, tableName, e1ColumnName);

        try {
            this.connection = DBCPDataSource.getConnection();
            this.psFindE1FromE2 = this.connection.prepareStatement(requestFindE1FromE2);
            this.psFindE2FromE1 = this.connection.prepareStatement(requestFindE2FromE1);
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to get the connection or create prepared statement");
        }
    }

    public Set<E1> fromE2(E2 e2) throws DataAccessException {
        Set<E1> set = new HashSet<>();

        try {
            psFindE1FromE2.setLong(1, e2.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to set id (long) on psFindSessionFromInstructor");
        }

        try {
            ResultSet resultSet = psFindE1FromE2.executeQuery();

            try (DAO<E1> dao = getE1DAO()) {
                while (resultSet.next()) {
                    long idSession = resultSet.getLong("ID_%s".formatted(e1ColumnName));
                    E1 e1 = dao.find(idSession).orElseThrow(() -> new IdentifiableNotFoundException(idSession));
                    set.add(e1);
                }
            }

        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to fetch result set");
        }

        return set;
    }

    public Set<E2> fromE1(E1 e1) throws DataAccessException {
        Set<E2> set = new HashSet<>();

        try {
            psFindE2FromE1.setLong(1, e1.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to set id (long) on psFindSessionFromInstructor");
        }

        try {
            ResultSet resultSet = psFindE2FromE1.executeQuery();

            try (DAO<E2> dao = getE2DAO()) {
                while (resultSet.next()) {
                    long idSession = resultSet.getLong("ID_%s".formatted(e1ColumnName));
                    E2 e2 = dao.find(idSession).orElseThrow(() -> new IdentifiableNotFoundException(idSession));
                    set.add(e2);
                }
            }

        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to fetch result set");
        }

        return set;
    }

    protected abstract DAO<E1> getE1DAO() throws DataAccessException;

    protected abstract DAO<E2> getE2DAO() throws DataAccessException;


}
