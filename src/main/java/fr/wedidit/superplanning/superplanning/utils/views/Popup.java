package fr.wedidit.superplanning.superplanning.utils.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Popup {

    private Popup() {}

    /**
     * Create a JavaFX window with a text.
     *
     * @param title title of the window
     * @param message message of the window
     */
    public static void popup(String title, String message) {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);

        Label labelMessage = new Label(message);

        Button buttonClosePopup = new Button("Fermer");
        buttonClosePopup.setOnAction(e -> popupWindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(labelMessage, buttonClosePopup);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 250);

        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    /**
     * Create a JavaFX window with a text to inform
     * the user.
     *
     * @param message message of the window
     */
    public static void info(String message) {
        popup("Informations", message);
    }

    /**
     * Create a JavaFX window with a text to give an error message.
     * Also log the error.
     * @param message message of the window
     */
    public static void error(String message) {
        popup("Error", message);
        log.error(message);
    }
}
