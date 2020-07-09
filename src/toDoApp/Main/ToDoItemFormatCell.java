package toDoApp.Main;

import toDoApp.Main.DataModel.ToDoItem;
import javafx.scene.control.ListCell;

public class ToDoItemFormatCell extends ListCell<ToDoItem> {

    @Override
    protected void updateItem(ToDoItem toDoItem, boolean empty) {
        super.updateItem(toDoItem, empty);
        setText(empty ? "" : toDoItem.getTitle());
    }


}
