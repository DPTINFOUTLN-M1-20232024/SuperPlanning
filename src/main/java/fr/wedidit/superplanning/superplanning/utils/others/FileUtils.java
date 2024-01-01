package fr.wedidit.superplanning.superplanning.utils.others;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;

/**
 * Used to handle file's stream.
 * Works for portable usage.
 */
@Slf4j
public class FileUtils {

    private FileUtils() {}

    /**
     *
     * @param fileName name of the file
     * @return The URL instance of the file
     */
    public static URL getResourceFile(String fileName) {
        return FileUtils.class.getResource("/%s".formatted(fileName));
    }

    /**
     *
     * @param folderName name of the folder containing the file.
     *                   If the file is in deep folder, you need to
     *                   put slash '/' between folder.
     * @param fileName name of the file
     * @return The URL instance of the file
     */
    public static URL getResourceFile(String folderName, String fileName) {
        return getResourceFile("%s/%s".formatted(folderName, fileName));
    }

    /**
     *
     * @param fileName name of the file
     * @return The InputStream instance of the file
     */
    public static InputStream getResourceFileAsStream(String fileName) {
        return FileUtils.class.getResourceAsStream("/%s".formatted(fileName));
    }

    /**
     *
     * @param folderName name of the folder containing the file.
     *                   If the file is in deep folder, you need to
     *                   put slash '/' between folder.
     * @param fileName name of the file
     * @return The InputStream instance of the file
     */
    public static InputStream getResourceFileAsStream(String folderName, String fileName) {
        return getResourceFileAsStream("%s/%s".formatted(folderName, fileName));
    }

    /**
     *
     * @param folderName name of the folder containing the file
     *                   If the file is in deep folder, you need to
     *                   put slash '/' between folder.
     * @param fileName name of the file
     * @return The content of the file
     */
    public static String getResourceFileContent(String folderName, String fileName) {
        InputStream inputStream = getResourceFileAsStream(folderName, fileName);
        try {
            return readFromInputStream(inputStream);
        } catch (IOException ioException) {
            log.error(ioException.getLocalizedMessage());
            return "?";
        }
    }

    /**
     *
     * @param inputStream The input stream
     * @return The content of the input stream
     * @throws IOException on IO error
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
