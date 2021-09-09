package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.Inventory;
import InventoryManagementSystem.Model.Part;
import InventoryManagementSystem.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainController class is responsible for simulating a user interface for managing the Inventory of Parts and Products
 *
 * @author Henry Trieu
 */
public class MainController implements Initializable
{
    Inventory inv;

    // Parts FXML variables
    @FXML private TextField partsSearchInput;
    @FXML private TextField productSearchInput;
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryLevelCol;
    @FXML private TableColumn<Part, Double> priceCol;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;

    // Products FXML variables
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Part, Integer> productIdCol;
    @FXML private TableColumn<Part, String> productNameCol;
    @FXML private TableColumn<Part, Integer> productInvLevelCol;
    @FXML private TableColumn<Part, Double> productPrice_costPerUnitCol;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private Button getAddPartButton;
    @FXML private Label exceptionLabel;

    /**
     * Constructor
     *
     * @param inv passed Inventory object to add Parts and allow other controllers to utilize the same data
     */
    public MainController(Inventory inv)
    {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partsTableView.setItems(inv.getAllParts());
        productsTableView.setItems(inv.getAllProducts());
    }

    /**
     * This method closes out the current screen and switches to the window for adding a new Part to the Inventory
     * @throws IOException
     */
    @FXML
    private void onActionAddPart() throws IOException
    {
        // Load the FXML file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagementSystem/View_Controller/AddPartController.fxml"));
        AddPartController controller = new AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();

        // Close the current window and build the new scene & display the Add Part window
        closeCurrentWindow();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method closes out the current screen and switches to the window for modifying a selected Part
     * from the Main Controller
     * @throws IOException
     */
    @FXML
    private void onActionModifyPart() throws IOException
    {
        // Get the selected Part
        Part part = partsTableView.getSelectionModel().getSelectedItem();

        if(part != null)
        {
            // Load the FXML file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagementSystem/View_Controller/ModifyPartController.fxml"));
            ModifyPartController controller = new ModifyPartController(inv, part);
            loader.setController(controller);
            Parent root = loader.load();

            // Close the current window and build the new scene & display the Add Part window
            closeCurrentWindow();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Prompts the user for confirmation and if confirmed, deletes the selected Part from the Inventory and refreshes
     * the list of Parts in the Inventory
     */
    @FXML
    private void onActionDeletePart()
    {
        // Checks if there is a part selected
        if(partsTableView.getSelectionModel().getSelectedItem() != null)
        {
            // Get the selected Part
            Part part = partsTableView.getSelectionModel().getSelectedItem();

            // Confirmation alert for deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you want to delete: " + part.getName());
            alert.setContentText("Click OK to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            // Delete if the user pressed OK
            if (result.get() == ButtonType.OK)
            {
                inv.deletePart(part);
                partsTableView.setItems(inv.getAllParts());
                partsTableView.refresh();
            }
        }
    }


    /**
     * This method closes out the current screen and switches to the window for adding a new Product to the Inventory
     * @throws IOException
     */
    @FXML
    private void onActionAddProduct() throws IOException
    {
        // Load the FXML file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagementSystem/View_Controller/AddProductController.fxml"));
        AddProductController controller = new AddProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();

        // Close the current window and build the new scene & display the Add Part window
        closeCurrentWindow();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method closes out the current screen and switches to the window for modifying a selected Product
     * from the Main Controller
     * @throws IOException
     */
    @FXML
    private void onActionModifyProduct() throws IOException
    {
        // Get the selected Part
        Product product = productsTableView.getSelectionModel().getSelectedItem();

        // Load the FXML file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagementSystem/View_Controller/ModifyProductController.fxml"));
        ModifyProductController controller = new ModifyProductController(inv, product);
        loader.setController(controller);
        Parent root = loader.load();

        // Close the current window and build the new scene & display the Add Part window
        closeCurrentWindow();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Prompts the user for confirmation and if confirmed, deletes the selected Product from the Inventory and refreshes
     * the list of Product in the Inventory
     */
    @FXML
    private void onActionDeleteProduct()
    {
        // Checks if there is a product selected
        if(productsTableView.getSelectionModel().getSelectedItem() != null)
        {
            // Get the selected Part
            Product prod = productsTableView.getSelectionModel().getSelectedItem();

            // Confirmation alert for deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete: " + prod.getName());
            alert.setContentText("Click OK to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            // Delete if the user pressed OK
            if (result.get() == ButtonType.OK)
            {
                // Check if the Product has any associated Parts
                if(prod.getAllAssociatedParts().isEmpty())
                {
                    exceptionLabel.setVisible(false);
                    inv.deleteProduct(prod);
                    productsTableView.setItems(inv.getAllProducts());
                    productsTableView.refresh();
                }
                else
                {
                    exceptionLabel.setVisible(true);
                }
            }
        }
    }

    /**
     * Selects the Part with the matching Part ID or sorts the list of all Parts based on the Part Name entered
     * @param event
     */
    @FXML
    private void searchPart(KeyEvent event) throws IOException
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String query = partsSearchInput.getText();

            // Perform lookup based on whether the search query is just the PartID or the Name of the Part
            if(onlyNumbersCheck(query) && !query.isEmpty())
            {
                Part part = inv.lookupPart(Integer.valueOf(query));
                partsTableView.getSelectionModel().select(part);
            }
            else
            {
                // if the search query is a String, then filter the results to show Parts that have Names within the query
                ObservableList<Part> results = FXCollections.observableArrayList();
                results.addAll(inv.lookupPart(query));
                partsTableView.setItems(results);
            }
        }
    }

    /**
     * Selects the Product with the matching Product ID or sorts the list of all Products based on the Product Name entered
     * @param event
     */
    @FXML
    private void searchProduct(KeyEvent event) throws IOException
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String query = productSearchInput.getText();

            // Perform lookup based on whether the search query is just the Product ID or the Name of the Product
            if(onlyNumbersCheck(query) && !query.isEmpty())
            {
                Product product = inv.lookupProduct(Integer.valueOf(query));
                productsTableView.getSelectionModel().select(product);
            }
            else
            {
                // if the search query is a String, then filter the results to show Parts that have Names within the query
                ObservableList<Product> results = FXCollections.observableArrayList();
                results.addAll(inv.lookupProduct(query));
                productsTableView.setItems(results);
            }
        }
    }

    /**
     * Prompts the user for exiting the application and if confirmed, closes out the application.
     */
    @FXML
    private void onActionExit()
    {
        // Confirmation alert for exiting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            Stage currentStage = (Stage)partsTableView.getScene().getWindow();
            currentStage.close();
        }
    }

    /**
     * This method closes out the current window
     */
    private void closeCurrentWindow()
    {
        Stage currentStage = (Stage)partsTableView.getScene().getWindow();
        currentStage.close();
    }

    /**
     * This method returns true if the String provided only contains numbers
     * @param s String to perform the logical check
     * @return true/false based on if @param s only contains numbers or not.
     */
    private boolean onlyNumbersCheck(String s)
    {
        boolean results = false;

        for(int i = 0; i <= s.length()-1; i++)
        {
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9')
            {
                results = true;
            }
            else
            {
                results = false;
            }
        }

        return results;
    }
}
