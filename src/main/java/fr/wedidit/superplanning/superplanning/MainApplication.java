package fr.wedidit.superplanning.superplanning;

import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Connection.fxml")));
            stage.setScene(new Scene(root));
            loadProperties();
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
