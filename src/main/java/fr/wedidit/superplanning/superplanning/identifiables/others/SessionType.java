package fr.wedidit.superplanning.superplanning.identifiables.others;

import javafx.scene.paint.Paint;
import lombok.Getter;

@Getter
public enum SessionType {

    CM("#8sg5sd"),
    TD("#8sg5sd"),
    TP("#8sg5sd"),
    CC("#8sg5sd"),
    CT("#8sg5sd");

    private final String colorHexa;
    private final Paint colorPaint;

    private SessionType(String colorHexa) {
        this.colorHexa = colorHexa;
        this.colorPaint = Paint.valueOf(this.colorHexa);
    }

}
