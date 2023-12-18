package fr.wedidit.superplanning.superplanning.account;

import lombok.Getter;

@Getter
public class AccountSecretary {

    private static boolean isLogged = false;

    private AccountSecretary() {}

    public static void logging() {
        isLogged = true;
    }

    public static void disconnect() {
        isLogged = false;
    }

}
