package fr.wedidit.superplanning.superplanning.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for JavaFX scene switching.
 */
class SceneSwitcher {

    public SceneSwitcher() {
        throw new IllegalStateException("Utility class shouldn't be instanced");
    }

    /**
     * Method to switch from the current scene to the one described in {@paramref fileName}"
     * @param scene The current JavaFX scene currently shown.
     * @param fileName The FXML file describing the new scene.
     * @throws IOException when the FXML file cannot be loaded.
     */
    static void switchToScene(Scene scene, String fileName) throws IOException {
        Stage stage = (Stage) scene.getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getClassLoader().getResource(fileName)));
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    /**
     * Method to switch from the scene in which {@paramref event} occurred to the one described in {@paramref fileName}"
     * @param event The current JavaFX event that provokes the scene change.
     * @param fileName The FXML file describing the new scene.
     * @throws IOException when the FXML file cannot be loaded.
     */
     static void switchToScene(Event event, String fileName) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        switchToScene(scene,fileName);
    }
}
