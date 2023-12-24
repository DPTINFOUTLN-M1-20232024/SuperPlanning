package fr.wedidit.superplanning.superplanning.utils.others;

import fr.wedidit.superplanning.superplanning.utils.controllers.SceneLoader;

import java.io.InputStream;
import java.net.URL;

public class FileUtils {

    private FileUtils() {}

    public static URL getRessourceFile(String fileName) {
        return FileUtils.class.getResource(fileName);
    }

    public static URL getRessourceFile(String folderName, String fileName) {
        return getRessourceFile("/%s/%s".formatted(folderName, fileName));
    }

    public static InputStream getRessourceFileAsStream(String fileName) {
        return FileUtils.class.getResourceAsStream(fileName);
    }

    public static InputStream getRessourceFileAsStream(String folderName, String fileName) {
        return getRessourceFileAsStream("/%s/%s".formatted(folderName, fileName));
    }

}
