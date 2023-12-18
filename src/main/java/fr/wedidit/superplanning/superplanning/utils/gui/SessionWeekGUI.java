package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class SessionWeekGUI extends AbstractSessionGUI {

    private static final int XINITPOS = 109;
    private static final int YINITPOS = 130;
    private static final int XSHIFT = 216;
    private static final int YSIZE = 600 - YINITPOS - 10;

    public static SessionWeekGUI of(AnchorPane edtFrame, Session session) {
        SessionWeekGUI sessionWeekGUI = new SessionWeekGUI(edtFrame, session);
        sessionWeekGUI.colorWidgets();
        sessionWeekGUI.moveWidgets();
        sessionWeekGUI.buildLabels();
        sessionWeekGUI.draw();
        return sessionWeekGUI;
    }

    private SessionWeekGUI(AnchorPane edtFrame, Session session) {
        super(edtFrame, session, XINITPOS, YINITPOS, XSHIFT, YSIZE);
    }

}
