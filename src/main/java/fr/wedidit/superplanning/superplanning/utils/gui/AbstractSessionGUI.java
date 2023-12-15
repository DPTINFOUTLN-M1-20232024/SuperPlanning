package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.utils.Point;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Slf4j
public class AbstractSessionGUI {

    protected static final String LABEL_COLOR = "#FFFFFF";
    private static final int START_HOUR = 8;
    private static final int START_MINUTE = 0;
    private static final int END_HOUR = 18;
    private static final int END_MINUTE = 0;


    private Session session;
    private final int dayOfWeekNumber;
    private final Point drawingRectangleCoords;
    private final Point drawingRectangleSize;

    // FXML
    private AnchorPane edtFrame;
    private Label moduleNameLabel;
    private Label instructorNameLabel;
    private Label roomNameLabel;
    private Rectangle sessionRectangle;

    protected AbstractSessionGUI(AnchorPane edtFrame, Session session, int startX, int startY, int shiftX, int totalY) {
        this.session = session;
        this.edtFrame = edtFrame;
        LocalDateTime localDateTime = session.getBegin().toLocalDateTime();
        this.dayOfWeekNumber = localDateTime.getDayOfWeek().getValue() - 1;
        Timestamp startDayTimestamp = Timestamp.valueOf(localDateTime.with(LocalTime.of(START_HOUR, START_MINUTE)));
        Timestamp endDayTimestamp = Timestamp.valueOf(localDateTime.with(LocalTime.of(END_HOUR, END_MINUTE)));
        long num = session.getBegin().getTime() - startDayTimestamp.getTime();
        long den = endDayTimestamp.getTime() - startDayTimestamp.getTime();
        float div = (float) num / den;
        div *= totalY;
        div += startY;
        this.drawingRectangleCoords = Point.of(
                startX + shiftX * this.dayOfWeekNumber,
                (int) div
        );
        num = session.getFinish().getTime() - session.getBegin().getTime();
        div = (float) num / den;
        div *= totalY;

        this.drawingRectangleSize = Point.of(
                shiftX,
                (int) div
        );
        this.moduleNameLabel = new Label(session.getModule().getName());
        this.instructorNameLabel = new Label(session.getInstructor().getLastname());
        this.roomNameLabel = new Label(session.getRoom().getName());
        this.sessionRectangle = new Rectangle();
    }

    protected void draw() {
        this.edtFrame.getChildren().add(sessionRectangle);
        this.edtFrame.getChildren().add(moduleNameLabel);
        this.edtFrame.getChildren().add(instructorNameLabel);
        this.edtFrame.getChildren().add(roomNameLabel);
    }

    public void colorWidgets() {
        this.sessionRectangle.setFill(session.getSessionType().getColorPaint());
        this.moduleNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        this.instructorNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        this.roomNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
    }

    public void moveWidgets() {
        this.sessionRectangle.setX(drawingRectangleCoords.getX());
        this.sessionRectangle.setY(drawingRectangleCoords.getY());
        this.sessionRectangle.setWidth(drawingRectangleSize.getX());
        this.sessionRectangle.setHeight(drawingRectangleSize.getY());

        // Label module name
        this.moduleNameLabel.setLayoutX(drawingRectangleCoords.getX());
        this.moduleNameLabel.setLayoutY(drawingRectangleCoords.getY());

        // Label instructor name
        this.instructorNameLabel.setLayoutX(drawingRectangleCoords.getX());
        this.instructorNameLabel.setLayoutY(drawingRectangleCoords.getY());

        // Label room name
        this.roomNameLabel.setLayoutX(drawingRectangleCoords.getX());
        this.roomNameLabel.setLayoutY(drawingRectangleCoords.getY());
    }

}
