package fr.wedidit.superplanning.superplanning.database;

import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class used to start the H2 server.
 */
@Slf4j
public class StartH2 {

    private static final String DDB_PORT = "9092";

    public static void main(String[] args) {
        try {
            PropertyLoader.loadPropertyFile("app.properties");
            Server server = Server.createTcpServer("-tcpPort", DDB_PORT, "-tcpAllowOthers", "-pgAllowOthers", "-ifNotExists", "-baseDir", "/tmp/db").start();
            log.info(server.getStatus());
        } catch (SQLException sqlException) {
            log.info("Server start error. " + sqlException.getLocalizedMessage());
        } catch (IOException sqlException) {
            log.info("Configuration error. " + sqlException.getLocalizedMessage());
        }

        try (Connection connection = DBCPDataSource.getConnection()) {
            RunScript.execute(connection,
                    new InputStreamReader(Objects.requireNonNull(FileUtils.getRessourceFileAsStream("db", "createDB.sql"))));
        } catch (SQLException sqlException) {
            log.error("Unable to execute createDB.sql file:%n%s".formatted(sqlException.getLocalizedMessage()));
        }

        try (Connection connection = DBCPDataSource.getConnection()) {
            RunScript.execute(connection,
                    new InputStreamReader(Objects.requireNonNull(FileUtils.getRessourceFileAsStream("db", "feedDB.sql"))));
        } catch (SQLException sqlException) {
            log.error("Unable to execute feedDB.sql file:%n%s".formatted(sqlException.getLocalizedMessage()));
        }
    }
}