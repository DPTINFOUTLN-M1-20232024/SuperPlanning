package fr.wedidit.superplanning.superplanning.account;

import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others.SecretaryConnectionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import lombok.Getter;

@Getter
public class AccountSecretary {

    private static boolean isLogged = false;

    private AccountSecretary() {}

    public static void disconnect() {
        isLogged = false;
    }

    public static void connect(SessionConnection sessionConnection) {
        try (SecretaryConnectionDAO secretaryConnectionDAO = new SecretaryConnectionDAO()) {
            if (secretaryConnectionDAO.getSecretaryFromConnection(sessionConnection)) {
                isLogged = true;
            }
        } catch (DataAccessException dataAccessException) {
            Popup.error(dataAccessException.getLocalizedMessage());
        }
    }

    public static boolean isConnected() {
        return isLogged;
    }
}
