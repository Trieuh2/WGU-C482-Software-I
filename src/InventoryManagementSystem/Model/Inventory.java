package InventoryManagementSystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Inventory that is used to manage the list of all Parts and Products.
 */
public class Inventory
{
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new Part to the list of all Parts in the Inventory.
     *
     * @param newPart the new part
     */
    public void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product to the list of all Products in the Inventory.
     *
     * @param newProduct the new product
     */
    public void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * Looks up the Part that matches with the Part ID passed.
     *
     * @param partId the part id
     * @return the part
     */
    public Part lookupPart(int partId)
    {
        // iterate through the Parts list and return the index of the Part with the matching partId
        for(int i = 0; i < allParts.size(); i++)
        {
            if(allParts.get(i).getId() == partId)
            {
                return allParts.get(i);
            }
        }

        return null;
    }

    /**
     * Looks up the Parts that have their names containing a part of the String passed
     *
     * @param partName the part name
     * @return the observable list
     */
    public ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        // iterate through the entire list of parts, create and return a new list with results that contain the string query
        for(int i = 0; i < allParts.size(); i++)
        {
            if(allParts.get(i).getName().contains(partName))
            {
                searchResults.add(allParts.get(i));
            }
        }

        return searchResults;
    }

    /**
     * Looks up the Product that matches the passed Product ID.
     *
     * @param productId the product id
     * @return the product
     */
    public Product lookupProduct(int productId)
    {
        // iterate through the Parts list and return the index of the Part with the matching partId
        for(int i = 0; i < allProducts.size(); i++)
        {
            if(allProducts.get(i).getId() == productId)
            {
                return allProducts.get(i);
            }
        }
        return null;
    }

    /**
     * Looks up the Products that have their names containing a part of the String passed
     *
     * @param productName the product name
     * @return the observable list
     */
    public ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        // iterate through the entire list of parts, create and return a new list with results that contain the string query
        for(int i = 0; i < allProducts.size(); i++)
        {
            if(allProducts.get(i).getName().contains(productName))
            {
                searchResults.add(allProducts.get(i));
            }
        }

        return searchResults;
    }


    /**
     * Updates the Part at the passed index with the new Part that is also passed in this method.
     *
     * @param index   the index of the Part that needs to be updated.
     * @param newPart the new Part
     */
    public void updatePart(int index, Part newPart)
    {
        allParts.set(index, newPart);
    }

    /**
     * Updates the Product at the passed index with the new Product that is also passed in this method.
     *
     * @param index      the index of the Product that needs to be updated.
     * @param newProduct the new Product
     */
    public void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * Attempts to remove the selected Part from the Inventory and returns whether the deletion was successful
     *
     * @param selectedPart the selected part
     * @return returns true if the Part was deleted successfully
     */
    public boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }

    /**
     * Attempts to remove the selected Product from the Inventory and returns whether the deletion was successful
     *
     * @param selectedProduct the selected product
     * @return returns true if the Product was deleted successfully
     */
    public boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets list of all Parts.
     *
     * @return the all Parts
     */
// Get methods
    public ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * Gets list of all Products.
     *
     * @return the all Products
     */
    public ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
