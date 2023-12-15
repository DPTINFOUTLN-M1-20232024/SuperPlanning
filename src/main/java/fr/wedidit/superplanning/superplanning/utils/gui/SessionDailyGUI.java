package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import fr.wedidit.superplanning.superplanning.identifiables.others.SessionType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SessionDailyGUI extends AbstractSessionGUI{

    //todo : Changer valeurs pour correspondre à l'IHM Journalier
    private static final int XINITPOS = -1;
    private static final int YINITPOS = -1;
    private static final int XSHIFT = -1;
    private static final int YSIZE = -1;

    public static SessionDailyGUI of(AnchorPane edtFrame, Session session) {
        SessionDailyGUI sessionDailyGUI = new SessionDailyGUI(edtFrame, session);
        sessionDailyGUI.colorWidgets();
        sessionDailyGUI.moveWidgets();
        sessionDailyGUI.draw();
        return sessionDailyGUI;
    }

    private SessionDailyGUI(AnchorPane edtFrame, Session session) {
        super(edtFrame, session, XINITPOS, YINITPOS, XSHIFT, YSIZE);
    }
}
