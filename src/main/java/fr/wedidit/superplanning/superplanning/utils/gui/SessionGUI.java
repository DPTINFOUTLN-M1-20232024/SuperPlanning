package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class SessionGUI {

    private static final int XINITPOS = 109;
    private static final int YINITPOS = 130;
    private static final int XSHIFT = 216;
    private static final int YSHIFT = 48;

    private Session session;
    private Calendar calendarDay; //Date of day
    private final int intDay;
    private final float floatHour;

    private Rectangle emplacementRectangle;
    private Label moduleNameLabel;
    private Label instructorNameLabel;
    private Label roomNameLabel;
    private static final String labelColor = "#FFFFFF";

    public static SessionGUI of(Session session) {
        SessionGUI sessionGUI = new SessionGUI(session);
        sessionGUI.prepareWidgets();
        sessionGUI.moveWidgets();
        return sessionGUI;
    }
    private SessionGUI(Session session) {
        this.calendarDay = Calendar.getInstance();
        this.calendarDay.setTimeInMillis(session.getBegin().getTime());
        this.intDay = this.calendarDay.get(Calendar.DAY_OF_WEEK);
        this.floatHour = this.sizeRectangle();
        this.session = session;
        this.emplacementRectangle = new Rectangle(SessionGUI.XSHIFT, SessionGUI.YSHIFT * this.sizeRectangle());
        this.moduleNameLabel = new Label(session.getModule().getName());
        this.instructorNameLabel = new Label(session.getInstructor().getLastname());
        this.roomNameLabel = new Label(session.getRoom().getName());
    }

    private void prepareWidgets() {
        /*
        Prepare widgets from the scene that have been initialised.
         */

        //todo : Rajouter la couleur changeable par rapport à la session
        this.emplacementRectangle.setFill(Paint.valueOf(ColorOfSession.CM));
        this.moduleNameLabel.setTextFill(Paint.valueOf(SessionGUI.labelColor));
        this.instructorNameLabel.setTextFill(Paint.valueOf(SessionGUI.labelColor));
        this.roomNameLabel.setTextFill(Paint.valueOf(SessionGUI.labelColor));
    }

    private void moveWidgets() {
        /*
        Move widgets from the scene to their right place.
         */
        float xLabelShift = (float)SessionGUI.XSHIFT/2;
        float yLabelShift = (float)SessionGUI.YSHIFT/6;
        int xInitShift = SessionGUI.XINITPOS * this.intDay;
        this.emplacementRectangle.setX(xInitShift);
        this.emplacementRectangle.setY(SessionGUI.YINITPOS);
        this.moduleNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        this.moduleNameLabel.setTranslateY(SessionGUI.YINITPOS + ((int)yLabelShift));
        this.instructorNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        this.instructorNameLabel.setTranslateY(SessionGUI.YINITPOS + ((int)yLabelShift)*3);
        this.roomNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        this.roomNameLabel.setTranslateY(SessionGUI.YINITPOS + ((int)yLabelShift)*5);
    }

    private float sizeRectangle() {
        /*
        Return the number of pixels that are necessary to make the
        rectangle.
         */
        return (this.session.getFinish().getTime() - this.session.getBegin().getTime())/((float)3600000); //3600000 millisecondes = 3600 secondes
    }
}
