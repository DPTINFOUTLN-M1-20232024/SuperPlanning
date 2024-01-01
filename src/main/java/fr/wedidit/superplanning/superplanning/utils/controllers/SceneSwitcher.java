package fr.wedidit.superplanning.superplanning.utils.controllers;

import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for JavaFX scene switching.
 */
@Slf4j
public class SceneSwitcher {

    private SceneSwitcher() {}

    /**
     * Method to switch from the current scene to the one described in {fileName}"
     * @param scene The current JavaFX scene currently shown.
     * @param views The FXML file describing the new scene.
     */
    public static void switchToScene(Scene scene, Views views) {
        Stage stage = (Stage) scene.getWindow();
        Parent root = SceneLoader.loadScene(views.getFileName());

        if (root == null) return;

        scene = new Scene(root);

        stage.setScene(scene);

        setupWindowDimensions(stage, views);
        centerWindow(stage);

        stage.show();

    }

    public static void centerWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void setupWindowDimensions(Stage stage, Views views) {
        stage.setMinWidth(views.getWidth());
        stage.setMaxWidth(views.getWidth());
        stage.setMinHeight(views.getHeight());
        stage.setMaxHeight(views.getHeight());
    }

    /**
     * Method to switch from the scene in which {event} occurred to the one described in {fileName}"
     * @param event The current JavaFX event that provokes the scene change.
     * @param views The FXML file describing the new scene.
     */
     public static void switchToScene(Event event, Views views) {
        Scene scene = ((Node) event.getSource()).getScene();
        switchToScene(scene, views);
     }
}
