package toDoApp.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import toDoApp.Main.DataModel.ToDoItem;
import toDoApp.Main.DataModel.ToDoItems;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class ControllerMain {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ListView<ToDoItem> checkList;

    @FXML
    private VBox leftPrevFields;

    @FXML
    private TextField titlePrevField;

    @FXML
    private TextArea notePrevArea;

    @FXML
    private DatePicker datePrev;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnToday;

    @FXML
    private RadioButton dueDateToggle;

    @FXML
    private ComboBox<Comparator<ToDoItem>> comboBoxComparators;
    @FXML
    private CheckBox revOrderCheckBox;

    private ContextMenu contextMenu;

    private ArrayList<Node> previewFields;

    private final ToDoItems toDoItems = ToDoItems.getInstance();
    private ObservableList<ToDoItem> toDoItemsList;

    private LocalDate dateTemp;

    private SortedList<ToDoItem> sortedList;
    private final LocalDate zeroDate = ToDoItems.getZeroDate();


    public void initialize() {
        toDoItemsList = toDoItems.getToDoItemsList();

        // factoring cells, adding context menu
        checkList.setCellFactory(listView -> {
            ToDoCheckBoxListCell toDoCheckBoxListCell = new ToDoCheckBoxListCell(ToDoItem::selectedProperty, new ToDoCheckListConverter());
            toDoCheckBoxListCell.checkListSetContextMenu(contextMenu);

            return toDoCheckBoxListCell;
        });

        // context menu
        contextMenu = new ContextMenu();

        MenuItem contextDelete = new MenuItem("Delete");
        contextDelete.setOnAction(handle -> {
            ToDoItem toDoItem = checkList.getSelectionModel().getSelectedItem();
            if (toDoItem != null) {
                toDoItems.deleteToDoItem(toDoItem);
            }
        });
        contextMenu.getItems().addAll(contextDelete);

        // disable right preview fields if no items selected on the checkList
        leftPrevFields.disableProperty().bind(checkList.getSelectionModel().selectedItemProperty().isNull());
        leftPrevFields.visibleProperty().bind(checkList.getSelectionModel().selectedItemProperty().isNotNull());
        // disable reverse sort checkbox
        revOrderCheckBox.disableProperty().bind(comboBoxComparators.getSelectionModel().selectedItemProperty().isNull());

        // fill the preview fields with values of the currently selected item


        checkList.getSelectionModel().selectedItemProperty().
                addListener((obs, toDoPrev, toDoCurrent) -> {

                    if (toDoCurrent != null) {
                        dueDateToggle.setSelected(toDoCurrent.isHasDueDate());
                        datePrev.visibleProperty().bind(dueDateToggle.selectedProperty());
                        btnToday.visibleProperty().bind(dueDateToggle.selectedProperty());

                        titlePrevField.setText(toDoCurrent.getTitle());
                        notePrevArea.setText(toDoCurrent.getNote());
                        datePrev.setValue(toDoCurrent.getDate().isAfter(zeroDate) ? toDoCurrent.getDate() : null);
                    }

                    dateTemp = datePrev.getValue();

                    dueDateToggle.selectedProperty().addListener((dueDateToggle, oldBool, newBool) -> {
                        if (!dueDateToggle.getValue()) {
                            datePrev.setValue(zeroDate);
                        } else {
                            datePrev.setValue(dateTemp != null ? dateTemp : LocalDate.now());
                        }
                        datePrev.valueProperty().addListener((observableDate, oldDate, newDate) -> {
                            if (dueDateToggle.getValue()) {
                                dateTemp = newDate;
                                datePrev.setValue(newDate != null ? newDate : LocalDate.now());
                            }
                        });
                    });
                });

        // sorting and adding items to checkList
        ObservableList<Comparator<ToDoItem>> comparators = FXCollections.observableArrayList(
                new ComparatorDueDate(),
                new ComparatorByName()
        );
        comboBoxComparators.setItems(comparators);

        sortedList = toDoItemsList.sorted();
        EventHandler<ActionEvent> reverseSortHandler = reverseSort();
        revOrderCheckBox.setOnAction(reverseSortHandler);

        checkList.setItems(sortedList);
    }

    @FXML
    public void addNewItemDialog() {
        Dialog<ToDoItem> newItemDialog = new Dialog<>();
        newItemDialog.initOwner(borderPane.getScene().getWindow());
        newItemDialog.setTitle("Add new to-do item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/toDoApp/Main/newItemDialog.fxml"));

        try {

            newItemDialog.getDialogPane().setContent(fxmlLoader.load());

            ButtonType btnOK = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            newItemDialog.getDialogPane().getButtonTypes().addAll(btnCancel, btnOK);

            newItemDialog.setResultConverter(buttonType -> {
                if (buttonType == btnOK) {
                    NewItemDialogController controller = fxmlLoader.getController();
                    return controller.createToDoItem();
                } else {
                    return null;
                }
            });

            Optional<ToDoItem> result = newItemDialog.showAndWait();
            result.ifPresent(toDoItems::addToDoItem);

        } catch (IOException e) {
            System.out.println("Error loading addNewItemDialog!" + e.getMessage());
        }
    }

    @FXML
    public void setBtnUpdate() {
        ToDoItem toDoItem = checkList.getSelectionModel().getSelectedItem();
        int index = checkList.getSelectionModel().getSelectedIndex();
        if (toDoItem != null) {
            toDoItems.updateToDoItem(toDoItem, titlePrevField.getText(), notePrevArea.getText(), datePrev.getValue());
            dateTemp = toDoItem.getDate().isEqual(ToDoItems.getZeroDate()) ? null : toDoItem.getDate();
            toDoItemsList.remove(toDoItem);
            toDoItemsList.add(index, toDoItem);
            checkList.getSelectionModel().select(index);
        }
    }

    @FXML
    public void deleteItemDialog() {
        ToDoItem toDoItem = checkList.getSelectionModel().getSelectedItem();
        Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Do you want to delete " + toDoItem.getTitle() + "?");

        Optional<ButtonType> result = deleteAlert.showAndWait();
        result.ifPresent(button -> {
            if (result.get() == ButtonType.OK) {
                toDoItems.deleteToDoItem(toDoItem);
            }
        });
    }

    @FXML
    public void setToday() {
        datePrev.setValue(LocalDate.now());
    }

    @FXML
    public void comboBoxSort() {
        Comparator<ToDoItem> comparator = comboBoxComparators.getSelectionModel().getSelectedItem();
        if (comparator != null) {
            sortedList.setComparator(comparator);
        }
    }

    @FXML
    public EventHandler<ActionEvent> reverseSort() {
        return event -> {
                if (event.getSource() instanceof CheckBox) {
                    revOrderCheckBox = (CheckBox) event.getSource();
                    if (revOrderCheckBox.isSelected()) {
                        System.out.println("reverse order performed");
                        sortedList.setComparator(sortedList.getComparator().reversed());
                    } else {
                        System.out.println(comboBoxComparators.getSelectionModel().getSelectedItem());
                        sortedList.setComparator(comboBoxComparators.getSelectionModel().getSelectedItem());
                    }
                }
            };
    }
}


