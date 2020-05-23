/**
 * A list to organize elements in ascending order.
 * @author Zachary Miles
 * @version 11-18-18
 * @param <T> The data type the list will contain.
 */
public class AscendinglyOrderedList<T>
{
    private T[] items;
    private int numItems;

    /**
     * Creates a new AscendinglyOrderList.
     */
    public AscendinglyOrderedList()
    {
        items = (T[]) new Object[3];
        numItems = 0;
    }

    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty()
    {
        return (numItems == 0);
    }

    /**
     * Provides the size of the list.
     * @return The size of the list.
     */
    public int size()
    {
        return numItems;
    }

    /**
     * Adds a new element to the ordered list,
     * the list will resize if necessary.
     * @param item The new element to be added to this list.
     * @return true if the new item was added, false if otherwise.
     */
    public boolean add(T item)
    {
        if (numItems == items.length)
        {
            resize();
        }
        int index = search(item.toString().split(" ")[0]);
        if(index < 0)
        {
            index = ~index;
            for (int pos = numItems-1; pos >= index; pos--)
            {
                items[pos+1] = items[pos];
            }
            items[index] = item;
            numItems++;
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Resizes the list if the list is full when adding a new element.
     */
    private void resize()
    {
        T[] temp = (T[]) new Object[(int) (numItems*1.5)];
        for(int i = 0; i < numItems; i++)
        {
            temp[i] = items[i];
        }
        items = temp;
    }

    /**
     * Retrieves an element from the list at the specified index from the parameter.
     * @param index The index to retrieve an element from.
     * @return The element located at the index specified in the parameter.
     * @throws ListIndexOutOfBoundsException If the index given as the parameter is less than zero or greater than list size - 1.
     */
    public T get(int index) throws ListIndexOutOfBoundsException
    {
        if (index >= 0 && index < numItems)
        {
            return items[index];
        }
        else
        {
            throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on get");
        }
    }

    /**
     * Removes an element at the index specified in the parameter from the list.
     * @param index The index where the element to be removed is located.
     * @throws ListIndexOutOfBoundsException If the index given as the parameter is less than zero or greater than list size - 1.
     */
    public void remove(int index) throws ListIndexOutOfBoundsException
    {
        if (index >= 0 && index < numItems)
        {
            for (int pos = index+1; pos < numItems; pos++)

            {
                items[pos-1] = items[pos];
            }
            numItems--;
            items[numItems] = null;
        }
        else
        {
            throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on remove");
        }
    }

    /**
     * Uses iterative Binary Search II discussed in class to search the list for the element specified in the parameter.
     * @param item The String name of the item to be searched for, will be matched against the resulting String of each item's toString.
     * @return The exact position of the item in the list if it exists, otherwise, a negative integer that encodes the location the item should be inserted is returned.
     */
    public int search(String item)
    {
        int result = -1;
        int high = numItems;
        int low = 0;
        while(high > low)
        {
            int mid = (high+low)/2;
            if(item.compareTo(items[mid].toString().split(" ")[0]) <= 0)
            {
                high = (mid);
            }
            else
            {
                low = (mid+1);
            }
        }
        if(items[high] != null && item.equals(items[high].toString().split(" ")[0]))
        {
            result = high;
        }
        else
        {
            result = ~high;
        }
        return result;
    }

    /**
     * Empties the list.
     */
    public void clear()
    {
        items = (T[]) new Object[3];
        numItems = 0;
    }

    /**
     * Returns a String representation of the list and its contents.
     * @return A String representation of the list and its contents.
     */
    public String toString()
    {
        String s = "";
        for(Object item : items)
        {
            if(item != null)
            {
                s += (item.toString() + " ");
            }
        }
        return s;
    }
}
