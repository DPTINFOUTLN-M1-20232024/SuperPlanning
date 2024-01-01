package fr.wedidit.superplanning.superplanning.database.exceptions;

public class OnlyReadAccessException extends UnsupportedOperationException {
    public OnlyReadAccessException() {
        super("The current objet is only readable");
    }
}
