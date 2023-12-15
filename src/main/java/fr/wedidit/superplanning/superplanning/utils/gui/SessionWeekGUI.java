package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.others.SessionType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;

@Getter
@Setter
@Slf4j
public class SessionWeekGUI extends AbstractSessionGUI {

    private static final int XINITPOS = 109;
    private static final int YINITPOS = 130;
    private static final int XSHIFT = 216;
    private static final int YSHIFT = 48;

    private Rectangle emplacementRectangle;
    protected final int intDay;

    public static SessionWeekGUI of(AnchorPane edtFrame, Session session) {
        SessionWeekGUI sessionWeekGUI = new SessionWeekGUI(edtFrame, session);
        sessionWeekGUI.prepareWidgets();
        //sessionWeekGUI.moveWidgets();
        sessionWeekGUI.draw();
        return sessionWeekGUI;
    }

    private SessionWeekGUI(AnchorPane edtFrame, Session session) {
        super(edtFrame, session);
        this.intDay = this.calendarDay.get(Calendar.DAY_OF_WEEK);
        this.emplacementRectangle = new Rectangle(SessionWeekGUI.XSHIFT, SessionWeekGUI.YSHIFT * super.getRectangleSize());
    }

    @Override
    protected void prepareWidgets() {
        /*
        Prepare widgets from the scene that have been initialised.
         */

        //todo : Rajouter la couleur changeable par rapport à la session
        this.emplacementRectangle.setFill(SessionType.CM.getColorPaint());
        super.moduleNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        super.instructorNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        super.roomNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
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
        super.moduleNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        super.moduleNameLabel.setTranslateY(SessionWeekGUI.YINITPOS + ((int)yLabelShift));
        super.instructorNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        super.instructorNameLabel.setTranslateY(SessionWeekGUI.YINITPOS + ((int)yLabelShift)*3);
        super.roomNameLabel.setTranslateX(xInitShift + ((int)xLabelShift));
        super.roomNameLabel.setTranslateY(SessionWeekGUI.YINITPOS + ((int)yLabelShift)*5);
    }

    @Override
    protected void draw() {
        super.draw();
        super.edtFrame.getChildren().add(emplacementRectangle);
    }
}
