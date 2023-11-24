package fr.wedidit.superplanning.superplanning.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Contains utility functions for
 * interacting with the Property
 * object.
 */
public class PropertyLoader {

    private PropertyLoader() {}

    /**
     * Load the database login/password
     * contained in the app.properties
     * file into the System properties.
     *
     * @param propertyFileName File name
     * @throws IOException If the file is not found
     */
    public static void loadPropertyFile(String propertyFileName) throws IOException {
        Properties properties = new Properties();

        // Get the file
        InputStream inputstream = PropertyLoader.class.getClassLoader().getResourceAsStream(propertyFileName);
        if (inputstream == null) throw new FileNotFoundException();

        // load the property object from the file
        properties.load(inputstream);

        // Apply the new property objet to the system
        System.setProperties(properties);
    }

}
