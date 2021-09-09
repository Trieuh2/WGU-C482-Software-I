package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.InHouse;
import InventoryManagementSystem.Model.Inventory;
import InventoryManagementSystem.Model.Outsourced;
import InventoryManagementSystem.Model.Part;
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
 * The AddPartController class is responsible for simulating a user interface that allows a user to modify a selected Part from
 * the Inventory
 *
 * @author Henry Trieu
 */
public class ModifyPartController implements Initializable
{
    Inventory inv;
    Part part;

    // Labels and TextFields
    @FXML private Label machineCompanyIDLabel;
    @FXML private Label exceptionsLabel;
    @FXML private TextField partIdTextField;
    @FXML private TextField nameInput;
    @FXML private TextField invInput;
    @FXML private TextField priceInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;
    @FXML private TextField machineCompanyIdInput;

    // RadioButtons and Buttons
    @FXML private RadioButton outsourcedBtn;
    @FXML private RadioButton inHouseBtn;
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;

    /**
     * Constructor
     *
     * @param inv  passed Inventory object to modify Parts and allow other controllers to utilize the same data
     * @param part selected part from the main controller that is going to be modified
     */
    public ModifyPartController(Inventory inv, Part part)
    {
        this.inv = inv;
        this.part = part;
    }

    // Pre-populates the fields with the data from the selected Part to get modified
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Set the Outsourced and In-House buttons into a ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();
        outsourcedBtn.setToggleGroup(toggleGroup);
        inHouseBtn.setToggleGroup(toggleGroup);

        // Set the label's text to the information from the Part
        partIdTextField.setText("" + part.getId());
        nameInput.setText("" + part.getName());
        priceInput.setText("" + part.getPrice());
        invInput.setText("" + part.getStock());
        maxInput.setText("" + part.getMax());
        minInput.setText("" + part.getMin());

        // Determines whether the Part was In-House or Outsourced
        if(part instanceof InHouse)
        {
            machineCompanyIdInput.setText("" + ((InHouse) part).getMachineId());
            machineCompanyIDLabel.setText("Machine ID");
            inHouseBtn.setSelected(true);
        }
        if(part instanceof Outsourced)
        {
            machineCompanyIdInput.setText("" + ((Outsourced) part).getCompanyName());
            machineCompanyIDLabel.setText("Company Name");
            outsourcedBtn.setSelected(true);
        }
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
     * This method checks the validity of the input in the TextFields from the user and if valid, modifies the selected
     * Part.
     * @throws IOException
     */
    @FXML
    private void onActionSaveBtn() throws IOException
    {
        int id = part.getId();
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
        // if there are no exceptions, update the part
        else
        {
            exceptionsLabel.setVisible(false);
            int currentPartIndex = inv.getAllParts().indexOf(part);

            if (inHouseBtn.isSelected())
            {
                InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                inv.updatePart(currentPartIndex, updatedPart);
            }
            else
            {
                Outsourced updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                inv.updatePart(currentPartIndex, updatedPart);
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
    // Checks if the provided String only contains numbers
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
