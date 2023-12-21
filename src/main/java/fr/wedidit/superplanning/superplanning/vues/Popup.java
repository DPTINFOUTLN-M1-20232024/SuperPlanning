package fr.wedidit.superplanning.superplanning.vues;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

    private Popup() {}

    public static void popup(String title, String message) {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);

        Label labelMessage = new Label(message);

        Button buttonClosePopup = new Button("Close");
        buttonClosePopup.setOnAction(e -> popupWindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(labelMessage, buttonClosePopup);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);

        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    public static void error(String message) {
        popup("Error", message);
    }
}
