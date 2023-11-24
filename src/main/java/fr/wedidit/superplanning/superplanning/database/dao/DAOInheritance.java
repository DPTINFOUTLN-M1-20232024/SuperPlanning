package fr.wedidit.superplanning.superplanning.database.dao;

import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;

import java.sql.SQLException;

/**
 * Class used to manipulate inheritance in a database.
 * The architecture follows the following logic:
 * the universal relation with NULLABLE for the
 * attributes of the descendants, with an attribute
 * (DISCRIMINATOR) which indicates the actual type.
 *
 * @param <D> The object linked to the DAO
 * @param <E> The type of object concerned among its peers
 * @param <C> The table columns concerned
 */
public abstract class DAOInheritance<D extends Identifiable, E extends Enum<E>, C extends Enum<C>> extends AbstractDAO<D> {

    private final Class<C> columnsEnumClass;
    private final E enumType;

    /**
     * @param tableEnum Name of the table in the database
     * @param enumType Enum value
     * @param columnsEnumClass Enum class containing all the columns in the table
     * @throws DataAccessException if error (see message)
     */
    protected DAOInheritance(Enum<?> tableEnum, E enumType, Class<C> columnsEnumClass) throws DataAccessException {
        super(tableEnum, buildColumns(columnsEnumClass));
        this.enumType = enumType;
        this.columnsEnumClass = columnsEnumClass;
    }

    @Override
    public void update() throws DataAccessException {
        try {
            psUpdate.setString(1, enumType.name());
        } catch (SQLException sqlException) {
            throw new DataAccessException(DAOInheritance.class,
                    sqlException,
                    "Unable to set the document name to %s".formatted(enumType.name()));
        }
        super.update();
    }

    /**
     * Put null value in each container
     * of the prepared statement. It's
     * avoid to get sql error.
     *
     * @throws SQLException if error (see message)
     */
    protected void initInheritancePSPersist() throws SQLException {
        // set DISCRIMINATOR
        psPersist.setObject(1, enumType.name());
        for (int i = 2; i < columnsEnumClass.getEnumConstants().length; i++) {
            psPersist.setObject(i, null);
        }
    }

    /**
     * Put null value in each container
     * of the prepared statement. It's
     * avoid to get sql error.
     *
     * @throws SQLException if error (see message)
     */
    protected void initInheritancePSUpdate() throws SQLException {
        // SKIP INDEX 0 AND DISCRIMINATOR
        for (int i = 2; i < columnsEnumClass.getEnumConstants().length; i++) {
            psUpdate.setObject(i, null);
        }
    }

    /**
     * Returns, as an array of character strings,
     * the name of each column described in an
     * enumeration.
     *
     * @param columnsEnumClass Enum class
     * @return the name of each column described in an enumeration
     * @param <E> The Enum type
     */
    public static <E extends Enum<E>> String[] buildColumns(Class<E> columnsEnumClass) {
        int enumAmount = columnsEnumClass.getEnumConstants().length;
        String[] finalColumns = new String[enumAmount - 1];
        // SKIP INDEX 0
        for (int i = 1; i < enumAmount; i++) {
            finalColumns[i - 1] = columnsEnumClass.getEnumConstants()[i].name();
        }
        return finalColumns;
    }

}
