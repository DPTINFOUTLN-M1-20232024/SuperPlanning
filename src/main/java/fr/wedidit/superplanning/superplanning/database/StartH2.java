package fr.wedidit.superplanning.superplanning.database;

import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.properties.PropertyLoader;
import fr.wedidit.superplanning.superplanning.utils.others.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class used to start the H2 server.
 */
@Slf4j
public class StartH2 {

    private static final String DDB_PORT = "9092";

    public static void main(String[] args) {
        try {
            Server server = Server.createTcpServer("-tcpPort", DDB_PORT, "-tcpAllowOthers", "-pgAllowOthers", "-ifNotExists", "-baseDir", "/tmp/db").start();
            log.info(server.getStatus());
        } catch (SQLException sqlException) {
            log.info("Server start error. " + sqlException.getLocalizedMessage());
        }

        PropertyLoader.loadPropertyFile();

        InputStreamReader createDBScript = new InputStreamReader((FileUtils.getResourceFileAsStream("db", "createDB.sql")));
        try (Connection connection = DBCPDataSource.getConnection()) {
            RunScript.execute(connection, createDBScript);
        } catch (SQLException sqlException) {
            log.error("Unable to execute createDB.sql file:%n%s".formatted(sqlException.getLocalizedMessage()));
        }

        InputStreamReader feedDBScript = new InputStreamReader(FileUtils.getResourceFileAsStream("db", "feedDB.sql"));
        try (Connection connection = DBCPDataSource.getConnection()) {
            RunScript.execute(connection, feedDBScript);
        } catch (SQLException sqlException) {
            log.error("Unable to execute feedDB.sql file:%n%s".formatted(sqlException.getLocalizedMessage()));
        }
    }
}