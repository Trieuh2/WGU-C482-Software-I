package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.InHouse;
import InventoryManagementSystem.Model.Inventory;
import InventoryManagementSystem.Model.Outsourced;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddPartController is responsible for simulating a user interface that allows a user to add a Part to the Inventory
 *
 * @author Henry Trieu
 */
public class AddPartController implements Initializable
{
    Inventory inv;

    // RadioButtons and Labels
    @FXML private RadioButton outsourcedBtn;
    @FXML private RadioButton inHouseBtn;
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;

    // Labels & Text Fields
    @FXML private Label machineCompanyIDLabel;
    @FXML private Label exceptionsLabel;
    @FXML private TextField nameInput;
    @FXML private TextField invInput;
    @FXML private TextField priceInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;
    @FXML private TextField machineCompanyIdInput;

    /**
     * Constructor
     *
     * @param inv passed Inventory object to add Parts and allow other controllers to utilize the same data
     */
    public AddPartController(Inventory inv)
    {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Set the Outsourced and In-House buttons into a ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();
        outsourcedBtn.setToggleGroup(toggleGroup);
        inHouseBtn.setToggleGroup(toggleGroup);
    }

    /**
     * This method changes the label to prompt for a Machine ID when the part being added is In-House
     */
    @FXML
    private void onActionInHouseBtn()
    {
        machineCompanyIDLabel.setText("Machine ID");
    }

    /**
     * This method changes the label to prompt for a Company Name when the part being added is Outsourced
     */
    @FXML
    private void onActionOutsourcedBtn()
    {
        machineCompanyIDLabel.setText("Company Name");
    }

    /**
     * This method checks the validity of the input in the TextFields from the user and if valid, adds the Part to the
     * Inventory.
     * @throws IOException
     */
    @FXML
    private void onActionSaveBtn() throws IOException {
        int id = (int) (Math.random() * 1000);
        String name = "";
        int stock = 0;
        double price = 0.00;
        int max = 0;
        int min = 0;
        int machineId = 0;
        String companyName = "";

        StringBuilder error = new StringBuilder("Exception: ");

        // Logical checking to see if the inputted values are valid
        // Checks Name input
        if (nameInput.getText().isBlank()) {
            error.append("\n - No data in Name field.");
        } else {
            name = nameInput.getText();
        }

        // Check number inputs
        String intRegex = "\\d+"; // Used for checking if String is an integer

        // Checks stock input
        try {
            stock = Integer.parseInt(invInput.getText());
        } catch (NumberFormatException e) {
            error.append("\n - Inventory is not an integer.");
        }

        // Checks price input
        try {
            price = Double.parseDouble(priceInput.getText());
        } catch (NumberFormatException e) {
            error.append("\n - Price/Cost is not a double.");
        }

        // Checks Max input
        try {
            max = Integer.parseInt(maxInput.getText());
        } catch (NumberFormatException e) {
            error.append("\n - Max is not an integer.");
        }

        // Checks Min input
        try {
            min = Integer.parseInt(minInput.getText());
        } catch (NumberFormatException e) {
            error.append("\n - Min is not an integer.");
        }

        // Compares max and min
        if (maxInput.getText().matches(intRegex) &&
                minInput.getText().matches(intRegex) &&
                min >= max) {
            error.append("\n - Min has to be lower than max.");
        }

        // Checks inventory value to ensure they are between the max and min values
        if (invInput.getText().matches(intRegex) &&
                maxInput.getText().matches(intRegex) &&
                minInput.getText().matches(intRegex) &&
                (stock > max) || (stock < min)) {
            error.append("\n - Stock has to be between the max and min values.");
        }

        // Checks Machine ID/Company Name input
        if (machineCompanyIdInput.getText().isBlank()) {
            // Cater the exception to Machine ID/Company Name depending on the RadioButton selected
            if (inHouseBtn.isSelected()) {
                error.append("\n - No data in Machine ID field.");
            } else {
                error.append("\n - No data in Company Name field.");
            }
        }
        // Checks to make sure Machine ID is an integer and sets it to the machineId
        else if (inHouseBtn.isSelected())
        {
            if (!onlyNumbersCheck(machineCompanyIdInput.getText()))
            {
                error.append("\n - Machine ID is not an integer.");
            }
            else
            {
                machineId = Integer.parseInt(machineCompanyIdInput.getText());
            }
        }
        // set the company name
        else
        {
            companyName = machineCompanyIdInput.getText();
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
            exceptionsLabel.setVisible(false);

            // Determine whether it is an InHouse Part or Outsourced Part and then add it to the Inventory
            if (inHouseBtn.isSelected())
            {
                InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                inv.addPart(newPart);
            }
            else
            {
                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                inv.addPart(newPart);
            }
            switchToMainController();
        }
    }

    /**
     * This method returns the user back to the main controller without adding any Part to the Inventory
     * @throws IOException
     */
    @FXML
    private void onActionCancelBtn() throws IOException
    {
        switchToMainController();
    }

    /**
     * This method closes out the current window and switches to the main controller view
     * @throws IOException
     */
    private void switchToMainController() throws IOException
    {
        // Closes the current window
        Stage stage = (Stage)saveBtn.getScene().getWindow();
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
