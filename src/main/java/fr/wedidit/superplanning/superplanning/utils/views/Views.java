package fr.wedidit.superplanning.superplanning.utils.views;

import lombok.Getter;

/**
 * Contains the enumeration of each fxml files.
 */
@Getter
public enum Views {

    CONNECTION("Connection.fxml", 600.0, 400.0),
    DAILY_VIEW("DailyView.fxml", 1218.0, 640.0),
    WEEK_VIEW("WeekView.fxml", 1218.0, 640.0),
    SECRETARY_ADDER_GRADE("SecretaryAdderGrade.fxml", 600.0, 400.0),
    SECRETARY_ADDER_INSTRUCTOR("SecretaryAdderInstructor.fxml", 600.0, 400.0),
    SECRETARY_ADDER_MODULE("SecretaryAdderModule.fxml", 600.0, 400.0),
    SECRETARY_ADDER_SESSION("SecretaryAdderSession.fxml", 600.0, 400.0),
    SECRETARY_ADDER_STUDENT("SecretaryAdderStudent.fxml", 600.0, 400.0),
    SECRETARY_MANAGEMENT("SecretaryManagement.fxml", 600.0, 400.0);

    private final String fileName;
    private final double width;
    private final double height;

    Views(String fileName, double width, double height) {
        this.fileName = fileName;
        this.width = width;
        this.height = height;
    }

}
