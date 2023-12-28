package fr.wedidit.superplanning.superplanning.controllers.validators;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public class ControllerValidator {

    private ControllerValidator() {}

    public static void widgetIsNotNull(Node node) throws ControllerValidatorException {
        if (node == null) {
            throw new ControllerValidatorException("The widget should not be null");
        }
    }

    public static void textFieldIsNotEmpty(TextField textField, String message) throws ControllerValidatorException {
        widgetIsNotNull(textField);
        if (textField.getText().isEmpty()) {
            throw new ControllerValidatorException(message);
        }
    }

    public static long getId(TextField textField) throws  ControllerValidatorException {
        textFieldIsNotEmpty(textField, "Vous devez remplir indiquer un id correct");
        String textFieldContent = textField.getText();
        long longValue;
        try {
            longValue = Long.parseLong(textFieldContent);
        } catch (NumberFormatException numberFormatException) {
            throw new ControllerValidatorException("Veuillez entrer un id correct (nombre entier)");
        }
        return longValue;
    }

}
