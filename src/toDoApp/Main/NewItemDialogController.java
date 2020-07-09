package toDoApp.Main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import toDoApp.Main.DataModel.ToDoItem;
import toDoApp.Main.DataModel.ToDoItems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class NewItemDialogController {
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private RadioButton dueDateToggle;
    @FXML
    private DatePicker datePrev;
    @FXML
    private Button btnToday;
    private LocalDate dateTemp;
    private final LocalDate zeroDate = ToDoItems.getZeroDate();
    private final DateTimeFormatter formatter = ToDoItems.getDtFormatter();

    public void initialize() {
        datePrev.setPromptText(ToDoItems.getPattern().toLowerCase());

        datePrev.visibleProperty().bind(dueDateToggle.selectedProperty());
        btnToday.visibleProperty().bind(dueDateToggle.selectedProperty());

        dateTemp = datePrev.getValue();

        dueDateToggle.selectedProperty().addListener((dueDateToggle, oldBool, newBool) -> {
            if (!dueDateToggle.getValue()) {
                datePrev.setValue(zeroDate);
            } else {
                datePrev.setValue(dateTemp != null ? dateTemp : LocalDate.now());
            }
            datePrev.valueProperty().addListener((observableDate, oldDate, newDate) -> {
                System.out.println("datetemp = " + dateTemp);
                if (dueDateToggle.getValue()) {
                    dateTemp = newDate;
                    datePrev.setValue(newDate != null ? newDate : LocalDate.now());
                }
            });

        });
    }

    public ToDoItem createToDoItem() {
        if (titleTextField.getText().isEmpty()) {
            return null;
        } else {
            return new ToDoItem(titleTextField.getText(), notesTextArea.getText(),
                    datePrev.getValue() != null ? datePrev.getValue() : zeroDate, false);
        }
    }

    public void setToday() {
        datePrev.setValue(LocalDate.now());
    }
}
