package fr.wedidit.superplanning.superplanning;

import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class MainApplication extends Application {

    public static void loadProperties() {
        try {
            PropertyLoader.loadPropertyFile("app.properties");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        Parent root = SceneLoader.loadScene("Connection.fxml");
        if (root == null) return;
        
        stage.setScene(new Scene(root));
        loadProperties();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
