package fr.wedidit.superplanning.superplanning.utils.gui;

import fr.wedidit.superplanning.superplanning.identifiables.others.Session;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionGUI {

    private static final int XINITPOS = 109;
    private static final int YINITPOS = 130;
    private static final int XSHIFT = 216;
    private static final int YSHIFT = 48;

    private Session session;
    private Rectangle emplacementRectangle;
    private Label moduleNameLabel;
    private Label instructorNameLabel;
    private Label roomNameLabel;

    public static SessionGUI of(Session session) {
        SessionGUI sessionGUI = new SessionGUI(session);
        sessionGUI.prepareWidgets();
        sessionGUI.moveWidgets();
        return sessionGUI;
    }
    private SessionGUI(Session session) {
        this.session = session;
        this.emplacementRectangle = new Rectangle(SessionGUI.XSHIFT, SessionGUI.YSHIFT);
        this.moduleNameLabel = new Label(session.getModule().getName());
        this.instructorNameLabel = new Label(session.getInstructor().getLastname());
        this.roomNameLabel = new Label(session.getRoom().getName());
    }

    private void prepareWidgets() {
        /*
        Prepare widgets from the scene that have been initialised.
         */
        //todo : Rajouter la couleur changeable par rapport à la session
        this.emplacementRectangle.setFill(Paint.valueOf("#444444"));
    }

    private void moveWidgets() {
        /*
        Move widgets from the scene to their right place.
         */

        this.emplacementRectangle.setX(SessionGUI.XINITPOS);
        this.emplacementRectangle.setY(SessionGUI.YINITPOS);
        this.moduleNameLabel.setTranslateX(SessionGUI.XINITPOS + (SessionGUI.XSHIFT>>1));
        this.moduleNameLabel.setTranslateY(SessionGUI.YINITPOS + ((SessionGUI.YSHIFT/3)));
    }
}
