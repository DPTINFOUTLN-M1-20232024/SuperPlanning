package fr.wedidit.superplanning.superplanning.account;

import lombok.Getter;

@Getter
public enum AccountType {

    STUDENT("DailyView.fxml"),
    SECRETARY("SecretaryManagement.fxml");

    private final String pageToSwitch;

    AccountType(String pageToSwitch) {
        this.pageToSwitch = pageToSwitch;
    }
}
