package toDoApp.Main.DataModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StoringDataTest {

    private static ToDoItems toDoItems;
    private static StoringData storingData;

    @BeforeAll
    static void beforeAll() {
        toDoItems = ToDoItems.getInstance();
        storingData = StoringData.getStoringData();
    }

    @Test
    void objectOutput() {
        try {
//            storingData.objectOutput(toDoItems.getToDoItemsList();
            System.out.println(storingData.getPath().toAbsolutePath());
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }

    }
}