package fr.wedidit.superplanning.superplanning.account.exceptions;

public class NoConnectedException extends RuntimeException {

    public NoConnectedException() {
        super("You are not logged in");
    }

}
