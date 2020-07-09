package toDoApp.Main;

import toDoApp.Main.DataModel.ToDoItem;

import java.util.Comparator;

public class ComparatorByName implements Comparator<ToDoItem> {

    @Override
    public int compare(ToDoItem t1, ToDoItem t2) {
        return t1.getTitle().compareTo(t2.getTitle());
    }

    @Override
    public String toString() {
        return "ByName";
    }


}
