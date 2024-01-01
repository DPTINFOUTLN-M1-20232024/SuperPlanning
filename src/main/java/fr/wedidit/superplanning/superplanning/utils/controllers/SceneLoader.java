package fr.wedidit.superplanning.superplanning.utils.controllers;

import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class SceneLoader {

    private SceneLoader() {}

    public static <T> T loadScene(String fxmlFileName) {
        try {
            return FXMLLoader.load(Objects.requireNonNull(FileUtils.getResourceFile("fxml", fxmlFileName)));
        } catch (IOException e) {
            log.error("Unable to load the FXML file \"%s\".".formatted(fxmlFileName));
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
