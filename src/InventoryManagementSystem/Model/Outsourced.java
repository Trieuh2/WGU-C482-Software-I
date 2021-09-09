package InventoryManagementSystem.Model;

/**
 * This is a subclass of Part, which dictates the Part object being Outsourced from a different company
 */
public class Outsourced extends Part
{
    private String companyName;

    /**
     * Instantiates a new Outsourced Part
     *
     * @param id          the id of the Outsourced Part
     * @param name        the name of the Outsourced Part
     * @param price       the price of the Outsourced Part
     * @param stock       the stock of the Outsourced Part
     * @param min         the min of the Outsourced Part
     * @param max         the max of the Outsourced Part
     * @param companyName the company name of the Outsourced Part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets company name of the Outsourced Part.
     *
     * @param companyName the name of the Company from the Outsourced Part
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * Gets company name of the Outsourced Part.
     *
     * @return the companyName
     */
    public String getCompanyName()
    {
        return companyName;
    }
}
