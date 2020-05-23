import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * A class used to run the Shopping Center program.
 * @author Zachary Miles and David Specht
 * @version 12-7-18
 */
public class Driver
{
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Runs the Shopping Center program.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        int currentTime = 0;
        System.out.println("Welcome to the Shopping Center!!!\n");
        System.out.println("Please specify stock");
        System.out.print(" How many items do you have?");
        int numItems = getUserInt();
        System.out.print("Please specify restocking amount:");
        int restockAmount = getUserInt();
        AscendinglyOrderedList<Item> items = new AscendinglyOrderedList<Item>();
        for(int index = 0; index < numItems; index++)
        {
            System.out.print(">>Enter item name : ");
            String itemName = getUserInput();
            System.out.print(">>How many " + itemName + "s? ");
            int amount = getUserInt();
            items.add(new Item(itemName, amount));
            System.out.println(amount + " items of " + itemName + " have been placed in stock.");
        }
        System.out.print("Please select the checkout line that should check out customers first (regular1/regular2/express): ");
        boolean invalid = false;
        int lineOrder = -1;
        do {
            invalid = false;
            String line = getUserInput();
            if(line.equalsIgnoreCase("regular1"))
            {
                lineOrder = 0;
            }
            else if(line.equalsIgnoreCase("regular2"))
            {
                lineOrder = 1;
            }
            else if(line.equalsIgnoreCase("express"))
            {
                lineOrder = 2;
            }
            else
            {
                System.out.println("ERROR: Invalid line, please enter express, regular1, or regular2.");
                invalid = true;
            }
        } while(invalid);
        QueueRA<Customer> regular1 = new QueueRA<Customer>();
        QueueRA<Customer> regular2 = new QueueRA<Customer>();
        QueueRA<Customer> express = new QueueRA<Customer>();
        AscendinglyOrderedList<Customer> customers = new AscendinglyOrderedList<Customer>();
        QueueRA<Customer> shopping = new QueueRA<Customer>();
        System.out.println(" ");
        System.out.println("Here are the choices to select from: ");
        System.out.println("    0. Close the Shopping Center.");
        System.out.println("    1. Customer enters the Shopping Center.");
        System.out.println("    2. Customer picks an item and places it in the shopping cart.");
        System.out.println("    3. Customer removes an item from the shopping cart.");
        System.out.println("    4. Customer is done shopping.");
        System.out.println("    5. Customer checks out.");
        System.out.println("    6. Print info about customers who are shopping.");
        System.out.println("    7. Print info about customers in checkout lines.");
        System.out.println("    8. Print info about items at or below re-stocking level.");
        System.out.println("    9. Reorder an item.");
        boolean exit = false;
        do {
            System.out.print("Make your menu selection now: ");
            int input = getUserInt();
            switch(input)
            {
            case 0:
                exit = true;
                break;
            case 1:
                enter(customers, shopping, currentTime);
                break;
            case 2:
                currentTime = shop(customers, items, currentTime);
                break;
            case 3:
                currentTime = remove(customers, currentTime);
                break;
            case 4:
                toCheckout(customers, shopping, regular1, regular2, express, currentTime);
                break;
            case 5:
                lineOrder = checkout(customers, shopping, regular1, regular2, express, currentTime, lineOrder);
                break;
            case 6:
                printCustomers(shopping, currentTime);
                break;
            case 7:
                printLines(regular1, regular2, express);
                break;
            case 8:
                printRestock(items, restockAmount);
                break;
            case 9:
                reorder(items);
                break;
            default:
                System.out.println("ERROR: Invalid menu choice, please enter 0, 1, 2, 3, 4, 5, 6, 7, 8, or 9.");
                break;
            }
            System.out.println();
        } while(!exit);
        System.out.println("The Shopping Center is closing...come back tomorrow.");
    }

    /**
     * A method used to have a new customer enter the shopping center.
     * @param customers A collection of customers to add the new customer to.
     * @param shopping A queue of customers currently shopping in the shopping center.
     * @param currentTime The amount of time the Shopping Center has been open.
     * @author David Specht
     * @throws IOException
     */
    public static void enter(AscendinglyOrderedList<Customer> customers, QueueRA<Customer> shopping, int currentTime) throws IOException
    {
        System.out.print("Enter customer name: ");
        String customerName = getUserInput();
        Customer entered = new Customer(customerName, currentTime);
        while(!customers.add(entered)) {
            System.out.println("Customer " + customerName + " is already in the Shopping Center.");
            System.out.print("Enter customer name: ");
            customerName = getUserInput();
            entered = new Customer(customerName, currentTime);
        }
        shopping.enqueue(entered);
        System.out.println("Customer " + customerName + " is now in the Shopping Center.");
    }

    /**
     * A method used to allow a user specified customer to add an item to his/her cart.
     * @param customers A collection of customers to pick a customer who is shopping from.
     * @param items A collection of items to choose an item to buy from.
     * @param currentTime The amount of time the Shopping Center has been open.
     * @return The updated amount of time the Shopping Center has been open.
     * @author Zachary Miles
     * @throws IOException
     */
    public static int shop(AscendinglyOrderedList<Customer> customers, AscendinglyOrderedList<Item> items, int currentTime) throws IOException
    {
        if(customers.isEmpty())
        {
            System.out.println("No one is in the Shopping Center!");
        }
        else
        {
            System.out.print(">>Enter customer name : ");
            String custName = getUserInput();
            int c = customers.search(custName);
            while(c<0 || !customers.get(c).getShopping()) {
                System.out.println(custName + " is not currently shopping.");
                System.out.print(">>Enter customer name : ");
                custName = getUserInput();
                c = customers.search(custName);
            }
            System.out.print(">>What item does " + custName + " want? ");
            String itemName = getUserInput();
            int i = items.search(itemName);
            if(i >= 0)
            {
                Item item = items.get(i);
                if(item.getQuantity() > 0)
                {
                    Customer curr = customers.get(c);
                    curr.addItem();
                    items.get(i).buy();
                    System.out.println("Customer " + custName + " has now " + curr.getItems() + " in the shopping cart.");
                    currentTime++;
                }
                else
                {
                    System.out.println("No " + itemName + "s in stock.");
                }
            }
            else
            {
                System.out.println("No " + itemName + "s in the Shopping Center.");
            }
        }
        return currentTime;
    }

    /**
     * A method to make the user specified customer remove an item from his/her cart.
     * @param customers The collection of customers.
     * @param currentTime The amount of time the Shopping Center has been open.
     * @return The updated amount of time the Shopping Center has been open.
     * @author Zachary Miles
     * @throws IOException
     */
    public static int remove(AscendinglyOrderedList<Customer> customers, int currentTime) throws IOException
    {
        if(customers.isEmpty())
        {
            System.out.println("No one is in the Shopping Center!");
        }
        else
        {
            System.out.print(">>Enter customer name : ");
            String custName = "";
            boolean found = false;
            int c = -1;
            do {
                custName = getUserInput();
                c = customers.search(custName);
                if(c >= 0 && customers.get(c).getShopping())
                {
                    found = true;
                }
                else
                {
                    System.out.println("Customer " + custName + " is not currently shopping.");
                    System.out.print(">>Enter customer name : ");
                }
            } while(!found);
            Customer curr = customers.get(c);

            if(curr.getItems() > 0)
            {
                curr.removeItem();
                System.out.println("Customer " + custName + " now has " + curr.getItems() + " items in the shopping cart.");
                currentTime++;
            }
            else
            {
                System.out.println("Customer " + custName + " has no items in their cart to remove.");
            }
        }
        return currentTime;
    }

    /**
     * A method that moves a customer who is finished shopping to a shopping line.
     * @param customers A list of all customers in the store.
     * @param shopping A queue of all customers who are currently shopping.
     * @param r1 One of two regular check-out line queues.
     * @param r2 One of two regular check-out line queues.
     * @param ex An express check-out line queue.
     * @param currentTime The amount of time the Shopping Center has been open.
     * @author David Specht
     * @throws IOException
     */
    public static void toCheckout(AscendinglyOrderedList<Customer> customers, QueueRA<Customer> shopping, QueueRA<Customer> r1,QueueRA<Customer> r2, QueueRA<Customer> ex, int currentTime) throws IOException {

        if(customers.isEmpty()) {
            System.out.println("No customers in the Shopping Center!");
            return;
        }

        Customer customer = shopping.dequeue();

        if(customer.getItems()==0) {
            String name = customer.getName();
            System.out.print("Should customer "+name+" leave or keep on shopping? Leave?(Y/N): ");
            String resp = getUserInput();
            if(resp.equalsIgnoreCase("y")) {
                System.out.println("Customer " + name + " is now leaving the shopping center");
                customers.remove(customers.search(name));
            }
            else {
                System.out.println("Customer " + name + " with 0 items returned to shopping.");
                customer.setShopping(true);
                customer.setTimeEnteredStore(currentTime);
                shopping.enqueue(customer);
            }
        }

        else {
            customer.setShopping(false);
            String line = "";
            if(r1.size()<=r2.size()) {
                if(customer.getItems()<=4 && r1.size() >= 2 * ex.size()) {
                    ex.enqueue(customer);
                    line = "express";
                }
                else {
                    r1.enqueue(customer);
                    line = "first";
                }
            }
            else {
                if(customer.getItems()<=4 && r2.size() >= 2 * ex.size()) {
                    ex.enqueue(customer);
                    line = "express";
                }
                else {
                    r2.enqueue(customer);
                    line = "second";
                }

            }
            System.out.println("After  "+ customer.getTimeInStore(currentTime) + " minutes in the Shopping Center customer " + customer.getName() + " with "+customer.getItems() + " items is now in the "+ line +" checkout line.");
        }

    }

    /**
     * A method that checks out customers from a Shopping Center and allows them to leave or continue shopping.
     * @param customers A list of all the customers in the Shopping Center
     * @param shopping A list of all customers who are currently shopping.
     * @param r1 One of two regular check-out line queues.
     * @param r2 One of two regular check-out line queues.
     * @param ex An express check-out line queue.
     * @param currentTime The amount of time the Shopping Center has been open.
     * @param order An integer value that indicates which line is next to check-out a customer.
     * @return An updated integer value that indicates which line is next to check-out a customer.
     * @author David Specht
     * @throws IOException
     */
    public static int checkout(AscendinglyOrderedList<Customer> customers, QueueRA<Customer> shopping, QueueRA<Customer> r1,QueueRA<Customer> r2, QueueRA<Customer> ex, int currentTime, int order) throws IOException {
        Customer customer = null;
        for(int i=0; i<=2; i++) {
            switch(order) {
            case 0:
                if(!r1.isEmpty()) {
                    customer = r1.dequeue();
                    i=4;
                }
                break;
            case 1:
                if(!r2.isEmpty()) {
                    customer = r2.dequeue();
                    i=4;
                }
                break;
            case 2:
                if(!ex.isEmpty()) {
                    customer = ex.dequeue();
                    i=4;
                }
                break;
            }
            order = ((order+1)%3);
        }
        if(customer == null) {
            System.out.println("No customers in any line.");
        }
        else {
            System.out.println("Should customer " + customer.getName() + " check out or keep on shopping? Checkout?(Y/N): ");
            String resp = getUserInput();
            if(resp.equalsIgnoreCase("y")) {
                System.out.println("Customer " + customer.getName() + "  is now leaving the Shopping Center.");
                customers.remove(customers.search(customer.getName()));
            }
            else {
                System.out.println("Customer " + customer.getName() + "  with " + customer.getItems() + " items returned to shopping.");
                customer.setShopping(true);
                customer.setTimeEnteredStore(currentTime);
                shopping.enqueue(customer);
            }

        }
        return order;
    }

    /**
     * A method that prints all the customers in a shopping center who are currently shopping.
     * @param shopping A queue of customers who are currently shopping in the Shopping Center.
     * @param currentTime The amount of time the Shopping Center has been open.
     * @author David Specht and Zachary Miles
     */
    public static void printCustomers(QueueRA<Customer> shopping, int currentTime) {
        if(shopping.isEmpty())
        {
            System.out.println("No customers are currently shopping.");
        }
        else
        {
            System.out.println("The following " + shopping.size() + " customers are in the Shopping Center: ");
            String[] shoppingInfo = shopping.toString().split(" ");
            int infoSize = shoppingInfo.length;
            for(int i = 0; i < infoSize; i+=3)
            {
                System.out.println("\tCustomer " + shoppingInfo[i] + " with " + shoppingInfo[i+1] + " items present for " + (currentTime - Integer.parseInt(shoppingInfo[i+2])) + " minutes.");
            }
        }
        System.out.println(" ");
    }

    /**
     * A method that prints information about the customers currently in each checkout line.
     * @param r1 One of two regular checkout line queues.
     * @param r2 One of two regular checkout line queues.
     * @param ex An express checkout line queue.
     * @author Zachary Miles and David Specht
     */
    public static void printLines(QueueRA<Customer> r1, QueueRA<Customer> r2, QueueRA<Customer> ex) {
        if(r1.isEmpty()) {
            System.out.println("No customers are in the first checkout line!");
        }
        else {
            System.out.println("The following " + r1.size() + " customers are in the first checkout line: ");
            String[] r1Info = r1.toString().split(" ");
            int infoSize = r1Info.length;
            for(int index = 0; index < infoSize; index += 3)
            {
                System.out.println("Customer " + r1Info[index] + " has " + r1Info[index+1] + " items in the shopping basket.");
            }
        }
        if(r2.isEmpty()) {
            System.out.println("No customers are in the second checkout line!");
        }
        else {
            System.out.println("The following " + r2.size() + " customers are in the second checkout line: ");
            String[] r2Info = r2.toString().split(" ");
            int infoSize = r2Info.length;
            for(int index = 0; index < infoSize; index += 3)
            {
                System.out.println("Customer " + r2Info[index] + " has " + r2Info[index+1] + " items in the shopping basket.");
            }
        }
        if(ex.isEmpty()) {
            System.out.println("No customers are in the express checkout line!");
        }
        else {
            System.out.println("The following " + ex.size() + " customers are in the express checkout line: ");
            String[] expressInfo = ex.toString().split(" ");
            int infoSize = expressInfo.length;
            for(int index = 0; index < infoSize; index += 3)
            {
                System.out.println("Customer " + expressInfo[index] + " has " + expressInfo[index+1] + " items in the shopping basket.");
            }
        }
    }


    /**
     * Prints all items with a quantity below the restock amount.
     * @param items The collection of items to print from.
     * @param restockAmount The restocking amount, which is set at the beginning of the program.
     * @author Zachary Miles
     */
    public static void printRestock(AscendinglyOrderedList<Item> items, int restockAmount)
    {
        int size = items.size();
        System.out.println("Items at re-stocking level: ");
        for(int index = 0; index < size; index++)
        {
            Item i = items.get(index);
            if(i.getQuantity() <= restockAmount)
            {
                System.out.println(i.getName() + " with " + i.getQuantity() + " items.");
            }
        }
    }

    /**
     * Reorders the user specified item from the shop's inventory.
     * @param items The collection of items in the shop's inventory.
     * @author Zachary Miles
     * @throws IOException
     */
    public static void reorder(AscendinglyOrderedList<Item> items) throws IOException
    {
        System.out.print("Enter item name to be re-ordered : ");
        String itemName = getUserInput();
        int i = items.search(itemName);
        if(i < 0)
        {
            System.out.println(itemName + " is not in stock!");
        }
        else
        {
            System.out.print("Enter number of " + itemName + "s to be re-ordered: ");
            int reorder = getUserInt();
            items.get(i).reStock(reorder);
            System.out.println("Stock now has " + items.get(i).getQuantity() + " " + itemName + "s.");
        }
    }

    /**
     * Collects an integer input from the user.
     * @return An integer input from the user.
     * @throws IOException
     */
    private static int getUserInt() throws IOException
    {
        boolean valid = false;
        int output = -1;
        do
        {
            try
            {
                String in = br.readLine().trim();
                System.out.println(in);
                output = Integer.parseInt(in);
                valid = true;
            }
            catch(NumberFormatException exception)
            {
                System.out.println("ERROR: The value inputted must be an integer");
            }
        } while(!valid);
        return output;
    }

    /**
     * Retrieves String input from the user.
     * @return A String entered by the user.
     * @throws IOException
     */
    private static String getUserInput() throws IOException
    {
        String in = br.readLine().trim();
        System.out.println(in);
        return in;
    }
}
