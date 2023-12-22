package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.layout.AnchorPane;

public class SessionDailyGUI extends AbstractSessionGUI{

    private static final int XINITPOS = 404;
    private static final int YINITPOS = 96;
    private static final int XSHIFT = 404;
    private static final int YSIZE = 464;

    public static SessionDailyGUI of(AnchorPane edtFrame, Session session) {
        SessionDailyGUI sessionDailyGUI = new SessionDailyGUI(edtFrame, session);
        sessionDailyGUI.colorWidgets();
        sessionDailyGUI.moveWidgets();
        sessionDailyGUI.buildLabels();
        sessionDailyGUI.draw();
        return sessionDailyGUI;
    }

    private SessionDailyGUI(AnchorPane edtFrame, Session session) {
        super(edtFrame, session, XINITPOS, YINITPOS, XSHIFT, YSIZE, false);
    }
}
