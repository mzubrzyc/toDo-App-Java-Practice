package toDoApp.Main;

import javafx.util.StringConverter;
import toDoApp.Main.DataModel.ToDoItem;

public class ToDoCheckListConverter extends StringConverter<ToDoItem> {

    @Override
    public String toString(ToDoItem toDoItem) {
        return toDoItem.getTitle();
    }

    @Override
    public ToDoItem fromString(String s) {
        return null;
    }


}
