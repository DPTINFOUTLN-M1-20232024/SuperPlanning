package fr.wedidit.superplanning.superplanning.identifiables.others;

import javafx.scene.paint.Paint;

public enum SessionType {

    CM("#F37302"),
    TD("#F39C4F"),
    TP("#47991E"),
    CC("#E91D1D"),
    CT("#E91D1D");

    private final String colorHexa;

    private SessionType(String colorHexa) {
        this.colorHexa = colorHexa;
    }

    public String getColorHexa() {
        return colorHexa;
    }

    public Paint getColorPaint() {
        return Paint.valueOf(this.colorHexa);
    }

}
