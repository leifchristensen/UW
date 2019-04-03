package css241;

/**
 * @author TCSS143
 * @version Lab 5
 */
public class GroceryItemOrder {
    /** Name of the grocery item */
    private String name;
    /** Quantity of the grocery item */
    private int quantity;
    /** The amount one unit costs of the grocery item */
    private double pricePerUnit;

    /**
     * GroceryItemOrder constructor
     *
     * @param name A string value that represents the name
     * @param quantity A int value that represents the amount
     * @param pricePerUnit A double value that represents the price per unit
     */
    public GroceryItemOrder (String name, int quantity, double pricePerUnit) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    /**
     * Return the total cost of a grocery order 
     *
     * @return A double value representing the total cost of the order
     */
    public double getCost() {
        return pricePerUnit * quantity;
    }

    /**
     * Sets the quantity to the passed in value
     *
     * @param quantity The amount of units
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}