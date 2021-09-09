package InventoryManagementSystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product object is an object that contains associated parts that make up the product.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Instantiates a new Product object.
     *
     * @param id    the id of the Product
     * @param name  the name of the Product
     * @param price the price of the Product
     * @param stock the stock of the Product
     * @param min   the min of the Product
     * @param max   the max of the Product
     */
// Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets id of the Product.
     *
     * @param id the id of the Product
     */
// Mutator methods
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Sets name of the Product.
     *
     * @param name the name of the Product
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets price of the Product.
     *
     * @param price the price of the Product
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Sets stock of the Product.
     *
     * @param stock the current stock of the Product
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * Sets min of the Product.
     *
     * @param min the minimum stock level of the Product
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * Sets maximum stock level of the Product.
     *
     * @param max the max of the Product
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * Adds the Part to the associated list of Parts that make up the Product
     *
     * @param part the part of the Product
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * Deletes the associated part from the list that makes up the Product
     *
     * @param selectedAssociatedPart the selected associated part
     * @return the boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        try{
            associatedParts.remove(selectedAssociatedPart);
        }
        catch(Exception e)
        {

        }
        return false;
    }

    /**
     * Gets id of the Product.
     *
     * @return the id of the Product.
     */
// Accessor methods
    public int getId()
    {
        return id;
    }

    /**
     * Gets name of the Product.
     *
     * @return the name of the Product.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets price of the Product.
     *
     * @return the price of the Product.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Gets the current stock level of the Product.
     *
     * @return the current stock level of the Product.
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * Gets minimum stock level of the Product.
     *
     * @return the minimum stock level of the Product.
     */
    public int getMin()
    {
        return min;
    }

    /**
     * Gets maximum stock level of the Product.
     *
     * @return the maximum stock level of the Product.
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Gets the list of all associated parts that make up the Product.
     *
     * @return the all associated parts of the Product.
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}
