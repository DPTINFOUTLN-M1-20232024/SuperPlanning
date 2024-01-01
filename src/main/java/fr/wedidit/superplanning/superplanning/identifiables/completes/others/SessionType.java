package fr.wedidit.superplanning.superplanning.identifiables.completes.others;

import javafx.scene.paint.Paint;
import lombok.Getter;

@Getter
public enum SessionType {

    CM("#F37302"),
    TD("#F39C4F"),
    TP("#47991E"),
    CC("#E91D1D"),
    CT("#E91D1D");

    private final String colorHexa;

    SessionType(String colorHexa) {
        this.colorHexa = colorHexa;
    }

    public Paint getColorPaint() {
        return Paint.valueOf(this.colorHexa);
    }

}
