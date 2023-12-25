package fr.wedidit.superplanning.superplanning.controllers.connections;

import fr.wedidit.superplanning.superplanning.account.AccountSecretary;
import fr.wedidit.superplanning.superplanning.account.AccountStudent;
import fr.wedidit.superplanning.superplanning.account.AccountType;
import fr.wedidit.superplanning.superplanning.account.SessionConnection;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionController {

    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldMail;

    @FXML
    public void onButtonConnectClick(ActionEvent actionEvent) {
        tryLogin(actionEvent);
    }

    @FXML
    public void onEnter(ActionEvent actionEvent) {
        tryLogin(actionEvent);
    }

    private void tryLogin(ActionEvent actionEvent) {
        String mail = textFieldMail.getText();
        String password = textFieldPassword.getText();

        SessionConnection sessionConnection = SessionConnection.of(mail, password);

        AccountStudent.connect(sessionConnection);
        if (AccountStudent.isConnected()) {
            SceneSwitcher.switchToScene(actionEvent, AccountType.STUDENT.getPageToSwitch());
            return;
        }

        AccountSecretary.connect(sessionConnection);
        if (AccountSecretary.isConnected()) {
            SceneSwitcher.switchToScene(actionEvent, AccountType.SECRETARY.getPageToSwitch());
            return;
        }

        Popup.error("Mail ou mot de passe éronné.");
    }

}
