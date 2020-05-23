/**
 * A class that keeps track of the items sold in a shopping
 * center.
 * @author Zachary Miles
 * @version 11-18-18
 */
public class Item
{
    private String name;
    private int quantity;

    /**
     * Creates a new item.
     * @param name The name of the new item.
     * @param quantity The quantity of the new item.
     */
    public Item(String name, int quantity)
    {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Gets the name of the item.
     * @return The name of the item.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the amount of the item that is in stock.
     * @return The amount of the item in stock.
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * Orders more of the specific item.
     * @param n The amount of items to be ordered.
     */
    public void reStock(int n)
    {
        quantity += n;
    }

    /**
     * Decreases the quantity of this item by one,
     * this is used when a customer adds this item
     * to their cart.
     */
    public void buy()
    {
        quantity--;
    }

    /**
     * Returns a String representation of the item, to
     * be used in print methods.
     * @return A String representation of the item.
     */
    public String toString()
    {
        return (name + " " + quantity);
    }
}
