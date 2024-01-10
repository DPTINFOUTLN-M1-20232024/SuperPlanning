package fr.wedidit.superplanning.superplanning;

import fr.wedidit.superplanning.superplanning.database.H2Server;
import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneLoader;
import fr.wedidit.superplanning.superplanning.utils.controllers.SceneSwitcher;
import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import fr.wedidit.superplanning.superplanning.utils.views.Views;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        Parent root = SceneLoader.loadScene(Views.CONNECTION.getFileName());
        if (root == null) return;

        Scene scene = new Scene(root);

        PropertyLoader.loadPropertyFile();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("SuperPlanning");
        stage.getIcons().add(new Image(FileUtils.getResourceFileAsStream("image", "Superplanning.png")));
        SceneSwitcher.setupWindowDimensions(stage, Views.CONNECTION);
        SceneSwitcher.centerWindow(stage);
        stage.show();
    }
    @Override
    public void stop() {
        H2Server.close();
    }

    public static void main(String[] args) {
        H2Server.open();
        launch(args);
    }
}
