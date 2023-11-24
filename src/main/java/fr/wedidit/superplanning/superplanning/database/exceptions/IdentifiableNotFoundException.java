package fr.wedidit.superplanning.superplanning.database.exceptions;

public class IdentifiableNotFoundException extends DataAccessException {
    public IdentifiableNotFoundException(long id) {
        super("Entity not found with id = %d".formatted(id));
    }
}
