package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SessionDailyGUI extends AbstractSessionGUI{

    //todo : Changer valeurs pour correspondre à l'IHM Journalier
    private static final int XINITPOS = -1;
    private static final int YINITPOS = -1;
    private static final int XSHIFT = -1;
    private static final int YSHIFT = -1;

    private Rectangle emplacementRectangle;

    public static SessionDailyGUI of(Session session) {
        SessionDailyGUI sessionDailyGUI = new SessionDailyGUI(session);
        sessionDailyGUI.prepareWidgets();
        sessionDailyGUI.moveWidgets();
        return sessionDailyGUI;
    }

    private SessionDailyGUI(Session session) {
        super(session);
        this.emplacementRectangle = new Rectangle(SessionDailyGUI.XSHIFT, SessionDailyGUI.YSHIFT * this.sizeRectangle());
    }

    @Override
    protected void prepareWidgets() {
        /*
        Prepare widgets from the scene that have been initialised.
         */

        //todo : Rajouter la couleur changeable par rapport à la session
        this.emplacementRectangle.setFill(Paint.valueOf(ColorOfSession.CM));
        this.moduleNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        this.instructorNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
        this.roomNameLabel.setTextFill(Paint.valueOf(AbstractSessionGUI.LABEL_COLOR));
    }

    protected void moveWidgets() {
        /*
        Move widgets from the scene to their right place.
         */
        float xLabelShift = (float) SessionDailyGUI.XSHIFT/2;
        float yLabelShift = (float) SessionDailyGUI.YSHIFT/6;
        this.emplacementRectangle.setX(SessionDailyGUI.XINITPOS);
        this.emplacementRectangle.setY(SessionDailyGUI.YINITPOS);
        this.moduleNameLabel.setTranslateX(SessionDailyGUI.XINITPOS + (int)xLabelShift);
        this.moduleNameLabel.setTranslateY(SessionDailyGUI.YINITPOS + ((int)yLabelShift));
        this.instructorNameLabel.setTranslateX(SessionDailyGUI.XINITPOS + (int)xLabelShift);
        this.instructorNameLabel.setTranslateY(SessionDailyGUI.YINITPOS + ((int)yLabelShift)*3);
        this.roomNameLabel.setTranslateX(SessionDailyGUI.XINITPOS + (int)xLabelShift);
        this.roomNameLabel.setTranslateY(SessionDailyGUI.YINITPOS + ((int)yLabelShift)*5);
    }
}
