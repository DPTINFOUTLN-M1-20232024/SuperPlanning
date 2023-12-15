package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;

@Getter
@Setter
@Slf4j
public abstract class AbstractSessionGUI {

    protected Session session;
    protected Calendar calendarDay; //Date of day
    protected final float rectangleSize;
    protected AnchorPane edtFrame;

    protected Label moduleNameLabel;
    protected Label instructorNameLabel;
    protected Label roomNameLabel;
    protected static final String LABEL_COLOR = "#FFFFFF";

    protected AbstractSessionGUI(AnchorPane edtFrame, Session session) {
        this.session = session;
        this.edtFrame = edtFrame;
        this.calendarDay = Calendar.getInstance();
        this.calendarDay.setTimeInMillis(session.getBegin().getTime());
        this.rectangleSize = this.sizeRectangle();
        this.moduleNameLabel = new Label(session.getModule().getName());
        this.instructorNameLabel = new Label(session.getInstructor().getLastname());
        this.roomNameLabel = new Label(session.getRoom().getName());
    }

    protected void draw() {
        this.edtFrame.getChildren().add(moduleNameLabel);
        this.edtFrame.getChildren().add(instructorNameLabel);
        this.edtFrame.getChildren().add(roomNameLabel);
    }

    protected abstract void prepareWidgets();

    protected abstract void moveWidgets();

    /**
     * @return the number of pixels that are necessary to make the
     *      rectangle.
     */
    private float sizeRectangle() {
        return (this.session.getFinish().getTime() - this.session.getBegin().getTime())/((float)3600000); //3600000 millisecondes = 3600 secondes
    }
}
