package fr.wedidit.superplanning.superplanning.utils.controllers;

import fr.wedidit.superplanning.superplanning.utils.views.Popup;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for JavaFX scene switching.
 */
@Slf4j
public class SceneSwitcher {

    private SceneSwitcher() {}

    /**
     * Method to switch from the current scene to the one described in {fileName}"
     * @param scene The current JavaFX scene currently shown.
     * @param fileName The FXML file describing the new scene.
     */
    public static void switchToScene(Scene scene, String fileName) {
        Stage stage = (Stage) scene.getWindow();
        stage.setResizable(true);
        Parent root = SceneLoader.loadScene(fileName);

        if (root == null) return;
        //root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getClassLoader().getResource("fxml/%s".formatted(fileName))));

        scene = new Scene(root);

        stage.setScene(scene);

        stage.show();

    }

    /**
     * Method to switch from the scene in which {event} occurred to the one described in {fileName}"
     * @param event The current JavaFX event that provokes the scene change.
     * @param fileName The FXML file describing the new scene.
     */
     public static void switchToScene(Event event, String fileName) {
        Scene scene = ((Node) event.getSource()).getScene();
        switchToScene(scene,fileName);
     }
}
