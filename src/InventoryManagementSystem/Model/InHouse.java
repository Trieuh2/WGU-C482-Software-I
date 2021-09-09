package InventoryManagementSystem.Model;

/**
 * This is a subclass of Part, which dictates the Part object being In-House
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Instantiates a new In-House Part
     *
     * @param id        the id of the In-House Part
     * @param name      the name of the In-House Part
     * @param price     the price of the In-House Part
     * @param stock     the stock of the In-House Part
     * @param min       the min of the In-House Part
     * @param max       the max of the In-House Part
     * @param machineId the machine id of the In-House Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets machine id of the In-House Part.
     *
     * @param machineId the Id of the InHouse Part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets machine id of the In-House Part.
     *
     * @return the machineId of the In-House Part.
     */
    public int getMachineId()
    {
        return machineId;
    }
}
