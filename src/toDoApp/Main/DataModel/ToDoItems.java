package toDoApp.Main.DataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToDoItems {

    private final ObservableList<ToDoItem> toDoItemsList;
    private static final ToDoItems toDoItems = new ToDoItems();
    private final static String pattern = "yyyy-MM-dd";
    private final static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(pattern);
    private static final String zeroDateStr = "1920-01-01";
    private static final LocalDate zeroDate = LocalDate.parse(zeroDateStr, dtFormatter);

    private ToDoItems() {
        toDoItemsList = FXCollections.observableArrayList();
    }

    public boolean addToDoItem(ToDoItem toDoItem) {
        boolean listNotContainsItem = !toDoItemsList.contains(toDoItem);
        if (toDoItem != null && listNotContainsItem) {
            toDoItemsList.add(toDoItem);
            return true;
        } else {
            return false;
        }
    }

    public void deleteToDoItem(ToDoItem toDoItem) {
        boolean listContainsItem = toDoItemsList.contains(toDoItem);
        if (toDoItem != null && listContainsItem) {
            toDoItemsList.remove(toDoItem);
        }
    }

    public boolean updateToDoItem(ToDoItem toDoItem, String title, String note, LocalDate localDate) {
        boolean listContainsItem = toDoItemsList.contains(toDoItem);
        if (toDoItem != null && listContainsItem) {
            toDoItem.setTitle(title);
            toDoItem.setNote(note);
            toDoItem.setDate(localDate != null ? localDate : ToDoItems.getZeroDate());
            return true;
        }
        return false;
    }

    public boolean removeToDoItem(ToDoItem toDoItem) {
        boolean listContainsItem = toDoItemsList.contains(toDoItem);
        if (toDoItem != null && listContainsItem) {
            toDoItemsList.remove(toDoItem);
            return true;
        } else {
            return false;
        }
    }

    public static ToDoItems getInstance() {
        return toDoItems;
    }

    public ObservableList<ToDoItem> getToDoItemsList() {
        return toDoItemsList;
    }

    public static DateTimeFormatter getDtFormatter() {
        return dtFormatter;
    }

    public static String getPattern() {
        return pattern;
    }

    public static LocalDate getZeroDate() {
        return zeroDate;
    }
}
