package fr.wedidit.superplanning.superplanning.database;

/**
 * Used to identify an object, particularly in
 * the database.
 */
public interface Identifiable {

    /**
     * Return the id of the Identifiable.
     *
     * @return The id of the Identifiable
     */
    long getId();

}
