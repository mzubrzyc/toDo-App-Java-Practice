package toDoApp.Main.DataModel;

import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StoringData {
    private final String fileName = "todo-items.dat";
    private final String sep = File.separator;
    private final Path filePath = FileSystems.getDefault().getPath(fileName);

    private static final StoringData instance = new StoringData();

    public static StoringData getStoringData() {
        return instance;
    }

    public Path getPath() {
        return this.filePath;
    }

    public void outputData(ObservableList<ToDoItem> toDoItems, DateTimeFormatter dt) {

        try (DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath.toFile())))) {
            for (ToDoItem item : toDoItems) {
                output.writeUTF(item.getTitle());
                output.writeUTF(item.getNote());
                output.writeUTF(item.getDate().format(dt));
                output.writeBoolean(item.getSelected());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void inputData(ObservableList<ToDoItem> toDoItems, DateTimeFormatter df) throws Exception {
        if (filePath.toFile().exists()) {
            try (DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath.toFile())))) {
                boolean eof = false;

                while (!eof) {
                    try {
                        String title = input.readUTF();
                        String note = input.readUTF();
                        String dateStr = input.readUTF();
                        LocalDate date = LocalDate.parse(dateStr, df);
                        Boolean selected = input.readBoolean();

                        ToDoItem toDoItem = new ToDoItem(title, note, date, selected);
                        toDoItems.add(toDoItem);

                    } catch (EOFException e) {
                        System.out.println("Reached end of file.");
                        eof = true;
                    }
                }
            }
        }
    }
}