package toDoApp.Main.DataModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class ToDoItem {
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty note = new SimpleStringProperty();

    private final BooleanProperty hasDueDate = new SimpleBooleanProperty();
    private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final BooleanProperty selected = new SimpleBooleanProperty();

    
    public ToDoItem(String title) {
        this.title.set(title);
        this.note.set("");
        this.date.set(ToDoItems.getZeroDate());
        this.selected.set(false);
        setHasDueDate(date.get().isAfter(ToDoItems.getZeroDate()));
    }

    public ToDoItem(String title, String note) {
        this.title.set(title);
        this.note.set(note);
        this.date.set(ToDoItems.getZeroDate());
        this.selected.set(false);
        setHasDueDate(date.get().isAfter(ToDoItems.getZeroDate()));
    }

    public ToDoItem(String title, String note, LocalDate localDate) {
        this.title.set(title);
        this.note.set(note);
        this.date.set(localDate);
        this.selected.set(false);
        setHasDueDate(date.get().isAfter(ToDoItems.getZeroDate()));
    }

    public ToDoItem(String title, String note, LocalDate localDate, Boolean selected) {
        this.title.set(title);
        this.note.set(note);
        this.date.set(localDate);
        this.selected.setValue(selected);
        setHasDueDate(date.get().isAfter(ToDoItems.getZeroDate()));
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getNote() {
        return note.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public boolean isHasDueDate() {
        return hasDueDate.get();
    }

    public BooleanProperty hasDueDateProperty() {
        return hasDueDate;
    }

    public void setHasDueDate(boolean hasDueDate) {
        this.hasDueDate.set(hasDueDate);
        if (!hasDueDate) {
            this.date.set(ToDoItems.getZeroDate());
        }
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        setHasDueDate(date.isAfter(ToDoItems.getZeroDate()));
        this.date.set(date);
    }

    public Boolean getSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String toString() {
        return title.get();
    }
}
