module fr.wedidit.superplanning.superplanning {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires commons.dbcp2;
    requires org.slf4j;
    requires com.h2database;
    requires java.management;

    opens fr.wedidit.superplanning.superplanning to javafx.fxml;
    exports fr.wedidit.superplanning.superplanning;
}