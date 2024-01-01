package fr.wedidit.superplanning.superplanning.properties;

import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Contains utility functions for
 * interacting with the Property
 * object.
 */
@Slf4j
public class PropertyLoader {

    private static final String PROPERTY_FILE_NAME = "app.properties";

    private PropertyLoader() {}

    /**
     * Load the database login/password
     * contained in the app.properties
     * file into the System properties.
     */
    public static void loadPropertyFile() {
        Properties properties = new Properties();

        // Get the file
        InputStream inputstream = FileUtils.getResourceFileAsStream(PROPERTY_FILE_NAME);

        // load the property object from the file
        try {
            properties.load(inputstream);
        } catch (IOException e) {
            log.error("Unable to setup properties file");
            log.error(e.getLocalizedMessage());
            return;
        }

        // Apply the new property objet to the system
        System.setProperties(properties);
    }

}
