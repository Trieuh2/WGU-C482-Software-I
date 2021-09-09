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
 * The ModifyProductController is responsible for simulating a user interface that allows a user to modify a selected
 * Product from the Inventory.
 *
 * @author Henry Trieu
 */
public class ModifyProductController implements Initializable {
    Inventory inv;
    private Product selectedProduct;
    private ObservableList<Part> parts = FXCollections.observableArrayList();

    // TableView and TableColumns
    @FXML private TableView allPartsTableView;
    @FXML private TableView partsListTableView;
    @FXML private TableColumn<Part, Integer> idAllPartsCol;
    @FXML private TableColumn<Part, String> nameAllPartsCol;
    @FXML private TableColumn<Part, Integer> invAllPartsCol;
    @FXML private TableColumn<Part, Double> costAllPartsCol;

    @FXML private TableColumn<Part, Integer> idPartsListCol;
    @FXML private TableColumn<Part, String> namePartsListCol;
    @FXML private TableColumn<Part, Integer> invPartsListCol;
    @FXML private TableColumn<Part, Double> costPartsListCol;

    // Buttons
    @FXML private Button addBtn;
    @FXML private Button cancelBtn;
    @FXML private Button removeBtn;
    @FXML private Button saveBtn;

    // TextFields
    @FXML private TextField partsSearchInput;
    @FXML private TextField productIdTextField;
    @FXML private TextField nameInput;
    @FXML private TextField invInput;
    @FXML private TextField priceInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;

    // Labels
    @FXML private Label exceptionsLabel;

    /**
     * Constructor
     *
     * @param inv     passed Inventory object to add Parts and allow other controllers to utilize the same data
     * @param product selected Product from the Main Controller that is going to be modified
     */
    public ModifyProductController(Inventory inv, Product product)
    {
        this.inv = inv;
        this.selectedProduct = product;
    }

    // Pre-populates the fields with the data from the selected Product to get modified
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Set the contents of the tables
        allPartsTableView.setItems(inv.getAllParts());

        parts = selectedProduct.getAllAssociatedParts();
        partsListTableView.setItems(parts);

        // Pre-populate the fields with the current values of the selected product
        productIdTextField.setText("" + selectedProduct.getId());
        nameInput.setText("" + selectedProduct.getName());
        priceInput.setText("" + selectedProduct.getPrice());
        invInput.setText("" + selectedProduct.getStock());
        maxInput.setText("" + selectedProduct.getMax());
        minInput.setText("" + selectedProduct.getMin());
    }

    /**
     * Looks up a part based on the Part ID or a portion of the Part Name in the table that contains the entire list of
     * Parts in the Inventory
     * @param event
     */
    @FXML
    private void searchPart(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String query = partsSearchInput.getText();

            // Perform lookup based on whether the search query is just the PartID or the Name of the Part
            if(onlyNumbersCheck(query) && !query.isEmpty())
            {
                Part part = inv.lookupPart(Integer.parseInt(query));
                allPartsTableView.getSelectionModel().select(part);
            }
            else
            {
                // if the search query is a String, then filter the results to show Parts that have Names within the query
                ObservableList<Part> results = FXCollections.observableArrayList();
                results.addAll(inv.lookupPart(query));
                allPartsTableView.setItems(results);
            }
        }
    }

    /**
     * Takes the selected Part from the list of ALL Parts (top table), and adds it to the list of Parts that will make
     * the Product (bottom table)
     */
    @FXML
    private void onActionAddBtn()
    {
        // Get the selected Part
        Part part = (Part) allPartsTableView.getSelectionModel().getSelectedItem();

        // variables to add to the listPartsTableView
        if (part != null) {
            parts.add(part);
            partsListTableView.setItems(parts);
        }
    }

    /**
     * Prompts the user and if confirmed, removes the selected Part from the list of Parts that will make the Product
     * (bottom table)
     */
    @FXML
    private void onActionRemoveBtn()
    {
        if(partsListTableView.getSelectionModel().getSelectedItem() != null) {
            // Get the index of the selected item from the partsListTableView
            int index = partsListTableView.getSelectionModel().getSelectedIndex();

            // Confirmation alert for deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Part");
            alert.setHeaderText("Are you sure you want to delete: " + parts.get(index).getName());
            alert.setContentText("Click OK to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            // Delete if the user pressed OK
            if (result.get() == ButtonType.OK) {
                // Remove the Part from the list that makes up the Product
                parts.remove(index);
                partsListTableView.setItems(parts);
            }
        }
    }

    /**
     * Checks the validity of the input within the fields and if valid, adds the Product to the Inventory
     * @throws IOException
     */
    @FXML
    private void onActionSaveBtn() throws IOException
    {
        int id = selectedProduct.getId();
        String name = "";
        int stock = 0;
        double price = 0.00;
        int max = 0;
        int min = 0;

        StringBuilder error = new StringBuilder("Exception: ");

        // Logical checking to see if the inputted values are valid
        // Check Name input
        if (nameInput.getText().isBlank()) {
            error.append("\n - No data in Name field.");
        }
        else
        {
            name = nameInput.getText();
        }

        // Check number inputs
        String intRegex = "\\d+"; // Used for checking if String is an integer

        // Checks stock input
        try {
            stock = Integer.parseInt(invInput.getText().toString());
        } catch (NumberFormatException e) {
            error.append("\n - Inventory is not an integer.");
        }

        // Checks price input
        try {
            price = Double.parseDouble(priceInput.getText().toString());
        }
        catch (NumberFormatException e) {
            error.append("\n - Price/Cost is not a double.");
        }

        // Checks Max input
        try {
            max = Integer.parseInt(maxInput.getText().toString());
        } catch (NumberFormatException e) {
            error.append("\n - Max is not an integer.");
        }

        // Checks Min input
        try {
            min = Integer.parseInt(minInput.getText().toString());
        } catch (NumberFormatException e) {
            error.append("\n - Min is not an integer.");
        }

        // Compares max and min
        if (maxInput.getText().matches(intRegex) &&
                minInput.getText().matches(intRegex) &&
                min >= max)
        {
            error.append("\n - Min has to be lower than max.");
        }

        // Checks inventory value to ensure they are between the max and min values
        if (invInput.getText().matches(intRegex) &&
                maxInput.getText().matches(intRegex) &&
                minInput.getText().matches(intRegex) &&
                (stock > max) || (stock < min))
        {
            error.append("\n - Stock has to be between the max and min values.");
        }

        // Display exceptions if they exist
        if (error.toString().compareTo("Exception: ") != 0)
        {
            exceptionsLabel.setText(error.toString());
            exceptionsLabel.setVisible(true);
        }
        // if there are no exceptions, add the new Part to the Inventory model and display it in the TableView
        else
        {
            // Hide the exceptions list
            exceptionsLabel.setVisible(false);

            // Create a new Product object, add the associated parts, and add the Product to the inventory
            Product prod = new Product(id, name, price, stock, min, max);
            for(int i = 0; i <= parts.size()-1; i++)
            {
                prod.addAssociatedPart(parts.get(i));
            }
            inv.updateProduct(inv.getAllProducts().indexOf(selectedProduct), prod);

            // Switch back to main screen
            switchToMainController();
        }
    }

    /**
     * Returns the user back to the main controller view without adding any Product to the Inventory
     * @throws IOException
     */
    @FXML
    private void onActionCancelBtn() throws IOException {
        switchToMainController();
    }

    /**
     * This method closes out the current window and switches to the Main Controller view
     * @throws IOException
     */
    private void switchToMainController() throws IOException {
        // Closes the current window
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();

        // Load the FXML file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryManagementSystem/View_Controller/MainController.fxml"));
        MainController controller = new MainController(inv);
        loader.setController(controller);
        Parent root = loader.load();

        // Build the scene & display the window
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
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
