package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public abstract class AbstractSessionGUI {

    protected Session session;
    protected Calendar calendarDay; //Date of day
    protected final float floatHour;

    protected Label moduleNameLabel;
    protected Label instructorNameLabel;
    protected Label roomNameLabel;
    protected static final String LABEL_COLOR = "#FFFFFF";

    protected AbstractSessionGUI(Session session) {
        this.calendarDay = Calendar.getInstance();
        this.calendarDay.setTimeInMillis(session.getBegin().getTime());
        this.floatHour = this.sizeRectangle();
        this.session = session;
        this.moduleNameLabel = new Label(session.getModule().getName());
        this.instructorNameLabel = new Label(session.getInstructor().getLastname());
        this.roomNameLabel = new Label(session.getRoom().getName());
    }

    protected abstract void prepareWidgets();

    protected abstract void moveWidgets();

    protected float sizeRectangle() {
        /*
        Return the number of pixels that are necessary to make the
        rectangle.
         */
        return (this.session.getFinish().getTime() - this.session.getBegin().getTime())/((float)3600000); //3600000 millisecondes = 3600 secondes
    }
}
