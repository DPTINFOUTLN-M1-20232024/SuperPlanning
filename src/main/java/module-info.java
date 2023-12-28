module fr.wedidit.superplanning.superplanning {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires commons.dbcp2;
    requires org.slf4j;
    requires com.h2database;
    requires java.management;
    requires com.google.common;
    requires mail;

    opens fr.wedidit.superplanning.superplanning to javafx.fxml;
    exports fr.wedidit.superplanning.superplanning;
    opens fr.wedidit.superplanning.superplanning.utils.views to javafx.fxml;
    exports fr.wedidit.superplanning.superplanning.utils.views;
    exports fr.wedidit.superplanning.superplanning.controllers.secretary;
    opens fr.wedidit.superplanning.superplanning.controllers.secretary to javafx.fxml;
    exports fr.wedidit.superplanning.superplanning.utils.controllers;
    opens fr.wedidit.superplanning.superplanning.utils.controllers to javafx.fxml;
    exports fr.wedidit.superplanning.superplanning.controllers.connections;
    opens fr.wedidit.superplanning.superplanning.controllers.connections to javafx.fxml;
    exports fr.wedidit.superplanning.superplanning.controllers.sessions;
    opens fr.wedidit.superplanning.superplanning.controllers.sessions to javafx.fxml;
}