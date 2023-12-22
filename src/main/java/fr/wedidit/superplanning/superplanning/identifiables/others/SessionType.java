package fr.wedidit.superplanning.superplanning.identifiables.others;

import javafx.scene.paint.Paint;

public enum SessionType {

    CM("#EBFB7A"),
    TD("#4BDFDF"),
    TP("#20DE59"),
    CC("#E21A1A"),
    CT("#E21A1A");

    private final String colorHexa;
    private Paint colorPaint;

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
