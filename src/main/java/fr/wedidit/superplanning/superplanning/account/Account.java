package fr.wedidit.superplanning.superplanning.account;

import fr.wedidit.superplanning.superplanning.account.exceptions.AlreadyConnectedException;
import fr.wedidit.superplanning.superplanning.account.exceptions.NoConnectedException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;

public class Account {

    private Account() {}

    private static Student studentAccount = null;

    public static boolean isConnected() {
        return Account.studentAccount != null;
    }

    public static void connect(Student studentAccount) throws AlreadyConnectedException {
        if (isConnected()) throw new AlreadyConnectedException();
        Account.studentAccount = studentAccount;
    }

    public static void disconnect() throws NoConnectedException {
        if (!isConnected()) throw new NoConnectedException();
        Account.studentAccount = null;
    }

    public static Student getStudentAccount() throws NoConnectedException {
        if (!isConnected()) throw new NoConnectedException();
        return studentAccount;
    }

}