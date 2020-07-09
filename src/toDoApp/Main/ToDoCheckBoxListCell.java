package toDoApp.Main;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import toDoApp.Main.DataModel.ToDoItem;
import toDoApp.Main.DataModel.ToDoItems;

import java.time.LocalDate;

public class ToDoCheckBoxListCell extends CheckBoxListCell<ToDoItem> {

    private final LocalDate zeroDate = ToDoItems.getZeroDate();

    public ToDoCheckBoxListCell(Callback<ToDoItem, ObservableValue<Boolean>> callback, StringConverter<ToDoItem> converter) {
        super(callback, converter);
    }

    @Override
    public void updateItem(ToDoItem toDoItem, boolean isEmpty) {
        super.updateItem(toDoItem, isEmpty);
        if (isEmpty) {
            setText("");
        } else {
            if (toDoItem.getDate().isAfter(zeroDate) && toDoItem.getDate().isBefore(LocalDate.now())) {
                setTextFill(Color.RED);
            } else if (toDoItem.getDate().isEqual(LocalDate.now())) {
                setTextFill(Color.BLUE);
            } else {
                setTextFill(Color.BLACK);
            }
        }
    }

    public void checkListSetContextMenu(ContextMenu contextMenu) {
        this.emptyProperty().addListener((obs, obsOld, obsNew) -> {
            if (obsNew) {
                this.setContextMenu(null);
            } else {
                this.setContextMenu(contextMenu);
            }
        });
    }
}
