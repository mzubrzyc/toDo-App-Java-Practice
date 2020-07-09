package toDoApp.Main;

import toDoApp.Main.DataModel.StoringData;
import toDoApp.Main.DataModel.ToDoItems;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final ToDoItems toDoItems = ToDoItems.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/toDoApp/Main/main.fxml"));

        primaryStage.setTitle("ToDo-App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        StoringData.getStoringData().inputData(toDoItems.getToDoItemsList(), ToDoItems.getDtFormatter());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        StoringData.getStoringData().outputData(toDoItems.getToDoItemsList(), ToDoItems.getDtFormatter());

    }
}
