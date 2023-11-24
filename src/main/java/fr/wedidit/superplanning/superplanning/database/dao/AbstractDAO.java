package fr.wedidit.superplanning.superplanning.database.dao;

import fr.wedidit.superplanning.superplanning.database.DBUtils;
import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the DAO interface.
 *
 * @param <E> Identifiable
 */
@Slf4j
public abstract class AbstractDAO<E extends Identifiable> implements DAO<E> {

    private final String tableName;
    protected final Connection connection;
    protected final PreparedStatement psPersist;
    protected final PreparedStatement psUpdate;
    private final PreparedStatement psFind;
    private final PreparedStatement psFindAll;


     /**
     * @param tableEnum Table enum
     * @param columnsName Name of each column in the table
     * @throws DataAccessException if error (see message)
     */
    protected AbstractDAO(Enum<?> tableEnum, String[] columnsName) throws DataAccessException {
        this.tableName = tableEnum.name();
        try {
            this.connection = DBCPDataSource.getConnection();
            this.psFind = connection.prepareStatement(DBUtils.createPSFind(tableName));
            this.psFindAll = connection.prepareStatement(DBUtils.createPSFindAll(tableName));
            this.psPersist = connection.prepareStatement(DBUtils.createPSPersist(tableName, columnsName), Statement.RETURN_GENERATED_KEYS);
            this.psUpdate = connection.prepareStatement(DBUtils.createPSUpdate(tableName, columnsName));
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "table: %s, persist: %s, update: %s".formatted(
                            tableName,
                            DBUtils.createPSPersist(tableName, columnsName),
                            DBUtils.createPSUpdate(tableName, columnsName)
                    )
            );
        }
    }

    /**
     * Executes the prepared statement previously
     * filled in with the child class.
     *
     * @return The identifiable
     * @throws DataAccessException if error (see message)
     */
    protected E persist() throws DataAccessException {
        long id = -1;
        try {
            psPersist.executeUpdate();
            ResultSet rs = psPersist.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to persist");
        }
        final long idCopy = id;
        return find(id).orElseThrow(() -> new IdentifiableNotFoundException(idCopy));
    }

    @Override
    public Optional<E> find(long id) throws DataAccessException {
        E entity = null;
        try {
            psFind.setLong(1, id);
            ResultSet rs = psFind.executeQuery();
            if (rs.next()) {
                entity = instantiateEntity(rs);
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to find");
        }
        if (entity == null) throw new IdentifiableNotFoundException(id);
        return Optional.of(entity);
    }

    @Override
    public DAOPage<E> findAll(int pageNumber, int pageSize) throws DataAccessException {
        List<E> entityList = new ArrayList<>();
        try {
            psFindAll.setInt(1, pageSize);
            psFindAll.setInt(2, (pageNumber - 1) * pageSize);
            ResultSet rs = psFindAll.executeQuery();
            while (rs.next()) entityList.add(instantiateEntity(rs));
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to findAll"
                    );
        }
        return new DAOPage<>(pageNumber, pageSize, entityList);
    }

    /**
     * Executes the prepared statement previously
     * filled in with the child class.
     *
     * @throws DataAccessException if error (see message)
     */
    public void update() throws DataAccessException {
        try {
            psUpdate.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to update");
        }
    }

    @Override
    public void remove(long id) throws DataAccessException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DBUtils.createSDelete(this.tableName, id));
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to remove");
        }
    }

    @Override
    public void close() throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to close");
        }
    }

    @Override
    public void clean() throws DataAccessException {
        try (Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM " + tableName);
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to clean");
        }
    }

    /**
     * Used to instantiate an identifiable
     * from a result set.
     *
     * @param resultSet The data source
     * @return The identifiable
     * @throws SQLException if error (see message)
     */
    protected abstract E instantiateEntity(ResultSet resultSet) throws SQLException;
}
