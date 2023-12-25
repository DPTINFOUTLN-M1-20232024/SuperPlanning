package fr.wedidit.superplanning.superplanning.utils.views;

import fr.wedidit.superplanning.superplanning.database.dao.DAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * From <a href="https://www.techgalery.com/2019/08/javafx-combo-box-with-search.html">this website</a>
 */
@Slf4j
public class AutoCompleteBox implements EventHandler<KeyEvent> {
    private final ComboBox<String> comboBox;
    private final ObservableList<String> data;
    private Integer sid;

    /**
     *
     * @param comboBox filled
     */
    public AutoCompleteBox(final ComboBox<String> comboBox) {
        this.comboBox = comboBox;
        this.data = comboBox.getItems();

        this.doAutoCompleteBox();
    }

    public AutoCompleteBox(final ComboBox<String> comboBox, Integer sid) {
        this.comboBox = comboBox;
        this.data = comboBox.getItems();
        this.sid = sid;

        this.doAutoCompleteBox();
    }

    public AutoCompleteBox(final ComboBox<String> comboBox, DAO<?> dao) throws DataAccessException {
        List<String> items = dao.findAll().resultList().stream().map(Object::toString).toList();
        comboBox.setItems(FXCollections.observableList(items));
        this.comboBox = comboBox;
        this.data = comboBox.getItems();
        this.doAutoCompleteBox();
    }

    private void doAutoCompleteBox() {
        this.comboBox.setEditable(true);
        this.comboBox.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(Boolean.TRUE.equals(newValue)){//mean onfocus
                this.comboBox.show();
            }
        });

        this.comboBox.getEditor().setOnMouseClicked(event ->{
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) return;
            this.comboBox.show();
        });

        this.comboBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> moveCaret(this.comboBox.getEditor().getText().length()));

        this.comboBox.setOnKeyPressed(t -> comboBox.hide());

        this.comboBox.setOnKeyReleased(AutoCompleteBox.this);

        if(this.sid!=null)
            this.comboBox.getSelectionModel().select(this.sid);
    }

    private void setItems() {
        ObservableList<String> list = FXCollections.observableArrayList();

        for (String datum : this.data) {
            String s = this.comboBox.getEditor().getText().toLowerCase();
            if (datum.toLowerCase().contains(s.toLowerCase())) {
                list.add(datum);
            }
        }

        if(list.isEmpty()) this.comboBox.hide();

        this.comboBox.setItems(list);
        this.comboBox.show();
    }

    private void moveCaret(int textLength) {
        this.comboBox.getEditor().positionCaret(textLength);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if ( keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN
                || keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT
                || keyEvent.getCode() == KeyCode.HOME
                || keyEvent.getCode() == KeyCode.END || keyEvent.getCode() == KeyCode.TAB
        ) {
            return;
        }

        if(keyEvent.getCode() == KeyCode.BACK_SPACE){
            String str = this.comboBox.getEditor().getText();
            if (str != null && !str.isEmpty()) {
                str = str.substring(0, str.length() - 1);
            }
            if(str != null){
                this.comboBox.getEditor().setText(str);
                moveCaret(str.length());
            }
            this.comboBox.getSelectionModel().clearSelection();
        }

        if(keyEvent.getCode() == KeyCode.ENTER && comboBox.getSelectionModel().getSelectedIndex()>-1)
            return;

        setItems();
    }

    public static long getIdFromSearchBar(ComboBox<String> comboBox) {
        String rightPart = comboBox.getValue().split("id=")[1];
        StringBuilder idString = new StringBuilder();
        for (int i = 0; i < rightPart.length() && Character.isDigit(rightPart.charAt(i)); i++) {
            idString.append(rightPart.charAt(i));
        }

        try {
            return Long.parseLong(idString.toString());
        } catch (NumberFormatException numberFormatException) {
            Popup.error("Unable to get the id field in text \"%s\"".formatted(comboBox.getValue()));
            return -1;
        }
    }
}