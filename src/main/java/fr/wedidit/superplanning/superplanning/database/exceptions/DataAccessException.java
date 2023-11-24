package fr.wedidit.superplanning.superplanning.database.exceptions;

import fr.wedidit.superplanning.superplanning.database.Identifiable;
import fr.wedidit.superplanning.superplanning.database.dao.DAO;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.SQLException;

@ToString
@EqualsAndHashCode(callSuper = true)
public class DataAccessException extends Exception {

    /**
     * @param daoClass DAO class affected by the error
     * @param sqlException SQL error
     * @param identifiable Object related to DAO
     * @param reason Cause of error
     * @param <S> DAO class concerned
     */
    public <S extends DAO<?>> DataAccessException(Class<S> daoClass, SQLException sqlException, Identifiable identifiable, String reason) {
        super("[%s] %s (%s):%n%s".formatted(daoClass.getName(), reason, identifiable.toString(), sqlException.getLocalizedMessage()));
    }

    /**
     * @param daoClass DAO class affected by the error
     * @param sqlException SQL error
     * @param reason Cause of error
     * @param <S> DAO class concerned
     */
    public <S extends DAO<?>> DataAccessException(Class<S> daoClass, SQLException sqlException, String reason) {
        super("[%s] %s:%n%s".formatted(daoClass.getName(), reason, sqlException.getLocalizedMessage()));
    }

    /**
     * @param reason Cause of error
     */
    protected DataAccessException(String reason) {
        super(reason);
    }

}
