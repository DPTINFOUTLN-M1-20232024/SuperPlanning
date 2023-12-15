package fr.wedidit.superplanning.superplanning.account.exceptions;

public class AlreadyConnectedException extends RuntimeException {

    public AlreadyConnectedException() {
        super("You are already logged in");
    }

}
