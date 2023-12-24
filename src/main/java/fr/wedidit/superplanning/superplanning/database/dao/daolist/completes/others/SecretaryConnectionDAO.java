package fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.account.SessionConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class SecretaryConnectionDAO implements AutoCloseable {

    private Connection connection;
    private PreparedStatement psFindSecretary;

    public SecretaryConnectionDAO() throws DataAccessException {

        String requestFindSecretary = """
                SELECT %s.MAIL
                FROM %s
                WHERE %s.MAIL = ?
                AND %s.HASH_PASSWORD = ?;
                """.formatted(TableType.SECRETARY_CONNECTION.name(),
                TableType.SECRETARY_CONNECTION.name(),
                TableType.SECRETARY_CONNECTION.name(),
                TableType.SECRETARY_CONNECTION.name());

        try {
            this.connection = DBCPDataSource.getConnection();
        } catch (SQLException sqlException) {
            log.error("Unable to get connection from DBCPDataSource");
        }

        try {
            this.psFindSecretary = this.connection.prepareStatement(requestFindSecretary);
        } catch (SQLException sqlException) {
            log.error("Unable to create prepared statement requestFindSecretary request from SecretaryConnectionDAO.");
            log.error("Request: %s".formatted(requestFindSecretary));
        }

    }

    public boolean getSecretaryFromConnection(SessionConnection sessionConnection) throws DataAccessException {
        try {
            psFindSecretary.setString(1, sessionConnection.getMail());
            psFindSecretary.setString(2, sessionConnection.getHashPassword());
            ResultSet rs = psFindSecretary.executeQuery();
            return rs.next();
        } catch (SQLException sqlException) {
            throw new DataAccessException(SecretaryConnectionDAO.class,
                    sqlException,
                    "Unable to get secretary from connection secretary");
        }

    }

    @Override
    public void close() throws DataAccessException {
        try {
            this.connection.close();
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to close");
        }
    }
}