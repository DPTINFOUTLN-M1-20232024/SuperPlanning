package fr.wedidit.superplanning.superplanning.database;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for writing queries.
 */
@Slf4j
public class DBUtils {

    private DBUtils() {}

    /**
     * Used to create a query to retrieve
     * the elements of a table based on
     * an identifier.
     *
     * @param tableName Name of the table concerned
     * @return The request
     */
    public static String createPSFind(String tableName) {
        return "SELECT * FROM " + tableName + " WHERE ID=?;";
    }

    /**
     * Used to create a query to retrieve
     * the elements of a table.
     *
     * @param tableName Name of the table concerned
     * @return The request
     */
    public static String createPSFindAll(String tableName) {
        return "SELECT * FROM " + tableName + " LIMIT ? OFFSET ?";
    }

    /**
     * Used to create the query for data
     * persistence.
     *
     * @param tableName Name of the table concerned
     * @param columnsName Name of all columnsName concerned
     * @return The request
     */
    public static String createPSPersist(String tableName, String... columnsName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ")
                .append(tableName)
                .append(" (");

        // Append columnsName name
        int n = columnsName.length;
        for (int i = 0; i < n - 1; i++) {
            stringBuilder.append(columnsName[i]).append(',');
        }
        stringBuilder.append(columnsName[n - 1]);

        stringBuilder.append(") VALUES (");
        stringBuilder.append("?,".repeat(n - 1));
        stringBuilder.append("?);");
        return stringBuilder.toString();
    }

    /**
     * Used to update data in database.
     *
     * @param tableName Name of the table concerned
     * @param columnsName Name of all columnsName concerned
     * @return The request
     */
    public static String createPSUpdate(String tableName, String... columnsName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ")
                .append(tableName)
                .append(" SET ");
        int n = columnsName.length;
        for (int i = 0; i < n - 1; i++) {
            stringBuilder.append(columnsName[i]).append("=?,");
        }
        stringBuilder.append(columnsName[n - 1]).append("=?").append(" WHERE ID=?;");
        return stringBuilder.toString();
    }

    /**
     * Used to delete data in database.
     *
     * @param tableName Name of the table concerned
     * @param id Identifier of the object in the database
     * @return The request
     */
    public static String createSDelete(String tableName, long id) {
        return "DELETE FROM " + tableName + " WHERE ID=" + id + ";";
    }

}
