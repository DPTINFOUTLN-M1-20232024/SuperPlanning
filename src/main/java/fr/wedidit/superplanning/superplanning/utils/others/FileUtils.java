package fr.wedidit.superplanning.superplanning.utils.others;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;

@Slf4j
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

    public static String getRessourceFileContent(String folderName, String fileName) {
        InputStream inputStream = getRessourceFileAsStream(folderName, fileName);
        log.info(String.valueOf(inputStream));
        log.info(folderName);
        log.info(fileName);
        try {
            return readFromInputStream(inputStream);
        } catch (IOException ioException) {
            log.error(ioException.getLocalizedMessage());
            return "?";
        }
    }

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
