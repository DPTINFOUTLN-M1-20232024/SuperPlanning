package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.others.SessionType;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class SessionWeekGUI extends AbstractSessionGUI {

    private static final int XINITPOS = 109;
    private static final int YINITPOS = 130;
    private static final int XSHIFT = 216;
    private static final int YSHIFT = 48;

    private Rectangle emplacementRectangle;
    protected final int intDay;

    public static SessionWeekGUI of(Session session) {
        SessionWeekGUI sessionWeekGUI = new SessionWeekGUI(session);
        sessionWeekGUI.prepareWidgets();
        sessionWeekGUI.moveWidgets();
        return sessionWeekGUI;
    }

    private SessionWeekGUI(Session session) {
        super(session);
        this.intDay = this.calendarDay.get(Calendar.DAY_OF_WEEK);
        this.emplacementRectangle = new Rectangle(SessionWeekGUI.XSHIFT, SessionWeekGUI.YSHIFT * this.sizeRectangle());
    }

    @Override
    protected void prepareWidgets() {
        /*
        Prepare widgets from the scene that have been initialised.
         */

        //todo : Rajouter la couleur changeable par rapport à la session
        this.emplacementRectangle.setFill(SessionType.CM.getColorPaint());
        this.moduleNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        this.instructorNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        this.roomNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
    }

    protected void moveWidgets() {
        /*
        Move widgets from the scene to their right place.
         */
        float xLabelShift = (float) SessionWeekGUI.XSHIFT/2;
        float yLabelShift = (float) SessionWeekGUI.YSHIFT/6;
        int xInitShift = SessionWeekGUI.XINITPOS * this.intDay;
        this.emplacementRectangle.setX(xInitShift);
        this.emplacementRectangle.setY(SessionWeekGUI.YINITPOS);
        this.moduleNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        this.moduleNameLabel.setTranslateY(SessionWeekGUI.YINITPOS + ((int)yLabelShift));
        this.instructorNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        this.instructorNameLabel.setTranslateY(SessionWeekGUI.YINITPOS + ((int)yLabelShift)*3);
        this.roomNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        this.roomNameLabel.setTranslateY(SessionWeekGUI.YINITPOS + ((int)yLabelShift)*5);
    }
}
