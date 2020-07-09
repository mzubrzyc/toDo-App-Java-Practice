package toDoApp.Main;

import toDoApp.Main.DataModel.ToDoItem;
import toDoApp.Main.DataModel.ToDoItems;

import java.time.LocalDate;
import java.util.Comparator;

public class ComparatorDueDate implements Comparator<ToDoItem> {

    private final static LocalDate zeroDate = ToDoItems.getZeroDate();

    @Override
    public int compare(ToDoItem it1, ToDoItem it2) {
        if (it1.getDate().isEqual(zeroDate) && it2.getDate().isEqual(zeroDate)) {
            return it1.getTitle().compareTo(it2.getTitle());
        } else if (it1.getDate().isEqual(zeroDate) && it2.getDate().isAfter(zeroDate)) {
            return 1;
        } else if (it1.getDate().isAfter(zeroDate) && it2.getDate().isEqual(zeroDate)) {
            return -1;
        } else {
            return it1.getDate().compareTo(it2.getDate());
        }
    }

    @Override
    public String toString() {
        return "DueDate";
    }

    @Override
    public Comparator<ToDoItem> reversed() {
        return (it1, it2) -> {
            if (it1.getDate().isEqual(zeroDate) && it2.getDate().isEqual(zeroDate)) {
                return it1.getTitle().compareTo(it2.getTitle());
            } else if (it1.getDate().isEqual(zeroDate) && it2.getDate().isAfter(zeroDate)) {
                return 1;
            } else if (it1.getDate().isAfter(zeroDate) && it2.getDate().isEqual(zeroDate)) {
                return -1;
            } else {
                return it1.getDate().compareTo(it2.getDate()) * -1;
            }
        };
    }
}
