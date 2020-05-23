/**
 * A class to store information about a customer.
 * @author David Specht
 * @version 11-18-18
 */
class Customer
{
    private String name;
    private int cartItems;
    private int entryTime;
    private boolean shopping;

    /**
     * Creates a new customer.
     * @param name The name of the customer.
     * @param entryTime The time the customer entered the Shopping Center.
     */
    public Customer(String name, int entryTime) {
        this.name = name;
        cartItems = 0;
        this.entryTime = entryTime;
        shopping = true;
    }

    /**
     * Returns the name of the customer.
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of items in the customer's cart.
     * @return The number of items in the customer's cart.
     */
    public int getItems() {
        return cartItems;
    }

    /**
     * Adds an item to the customer's cart.
     */
    public void addItem() {
        cartItems++;
    }

    /**
     * Removes an item from the customer's cart.
     */
    public void removeItem() {
        cartItems--;
    }

    /**
     * Returns the time this customer has spent in the store.
     * @param time The current time the Shopping Center has been open.
     * @return The amount of time the customer has been in the store.
     */
    public int getTimeInStore(int time) {
        return time-entryTime;
    }

    /**
     * Sets the time the customer entered the store.
     * @param time The time the customer entered the store.
     */
    public void setTimeEnteredStore(int time) {
        entryTime=time;
    }

    /**
     * Returns whether or not the customer is currently shopping or checking out.
     * @return true if the customer is currently shopping or false if the customer is checking out.
     */
    public boolean getShopping() {
        return shopping;
    }

    /**
     * A method that changes whether or not the customer is currently shopping or checking out.
     * @param s A boolean value representing whether or not the customer is shopping (true if they are, false otherwise).
     */
    public void setShopping(boolean s) {
        shopping = s;
    }

    /**
     * Returns a String representation of the customer.
     * @return A String representation of the customer.
     */
    public String toString() {
        return name+" "+cartItems+" "+entryTime;
    }

}
