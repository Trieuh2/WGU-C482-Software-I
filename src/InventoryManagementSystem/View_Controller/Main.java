package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to start the application
 *
 * @author Henry Trieu
 */
public class Main extends Application {
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        // Initialize the Inventory
        Inventory inv = new Inventory();

        // Load the FXML file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagementSystem/View_Controller/MainController.fxml"));
        MainController controller = new MainController(inv);
        loader.setController(controller);
        Parent root = loader.load();

        // Build the scene & display the window
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[]args)
    {
        launch(args);
    }
}
