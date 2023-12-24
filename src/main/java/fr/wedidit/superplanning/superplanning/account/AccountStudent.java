package fr.wedidit.superplanning.superplanning.account;

import fr.wedidit.superplanning.superplanning.account.exceptions.AlreadyConnectedException;
import fr.wedidit.superplanning.superplanning.account.exceptions.NoConnectedException;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.StudentConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;

public class AccountStudent {

    private AccountStudent() {}

    private static Student studentAccount = null;

    public static boolean isConnected() {
        return AccountStudent.studentAccount != null;
    }

    public static void connect(SessionConnection sessionConnection) throws AlreadyConnectedException {
        if (isConnected()) throw new AlreadyConnectedException();

        // Loading the student from (mail, password)
        Student student;
        try (StudentConnectionDAO studentConnectionDAO = new StudentConnectionDAO()) {
            student = studentConnectionDAO.getStudentFromConnection(sessionConnection);
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
            return;
        }

        AccountStudent.studentAccount = student;
    }

    public static void disconnect() throws NoConnectedException {
        if (!isConnected()) throw new NoConnectedException();
        AccountStudent.studentAccount = null;
    }

    public static Student getStudentAccount() throws NoConnectedException {
        if (!isConnected()) throw new NoConnectedException();
        return studentAccount;
    }

}
