/**
 * A queue to organize elements in first in first out order.
 * @author Zachary Miles and David Specht
 * @version 11-21-18
 */
public class QueueRA<T> implements QueueInterface<T>
{
    protected T[] items;
    protected int front;
    protected int back;
    protected int numItems;

    /**
     * Creates a new queue.
     */
    public QueueRA()
    {
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    /**
     * Checks if there are no elements in the queue.
     * @return true if the queue is empty.
     */
    public boolean isEmpty()
    {
        return numItems == 0;
    }

    /**
     * Adds a new item to the back of the queue.
     * @param newItem The new item to be added to the queue.
     */
    public void enqueue(T newItem)
    {
        if(numItems == items.length)
        {
            resize();
            items[back] = newItem;
            back = (back+1)%items.length;
            numItems++;
        }
        else
        {
            items[back] = newItem;
            back = (back+1)%items.length;
            numItems++;
        }
    }

    /**
     * Resizes the array when the array is full upon enqueueing.
     */
    protected void resize()
    {
        int queueSize = items.length;
        T[] temp = (T[]) new Object[(int) ((queueSize+1)*1.5)];
        for(int index = 0; index < numItems; index++)
        {
            temp[index] = items[(front+index)%queueSize];
        }
        front = 0;
        back = numItems;
        items = temp;
    }

    /**
     * Removes and returns the element at the front of the queue.
     * @return The item at the front of the queue.
     * @throws QueueException If there are no items in the queue.
     */
    public T dequeue() throws QueueException
    {
        if(numItems != 0)
        {
            T item = items[front];
            items[front] = null;
            front = (front+1)%items.length;
            numItems--;
            return item;
        }
        else
        {
            throw new QueueException("Queue exception on dequeue.");
        }
    }

    /**
     * Empties all elements from the queue.
     */
    public void dequeueAll()
    {
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    /**
     * Checks and returns the first element in the queue.
     * @return The first element in the queue.
     * @throws QueueException If there are no items in the queue.
     */
    public T peek() throws QueueException
    {
        if(numItems != 0)
        {
            return items[front];
        }
        else
        {
            throw new QueueException("Queue exception on peek.");
        }
    }

    /**
     * Returns the number of items in the queue.
     * @return The number of items in the queue.
     */
    public int size() {
        return numItems;
    }

    /**
     * Returns a String representation of the queue.
     * @return A String representation of the queue.
     */
    public String toString()
    {
        String queueString = "";
        if(numItems != 0)
        {
            for(int index = 0; index < numItems; index++)
            {
                queueString += (items[(front + index)%items.length].toString() + " ");
            }
        }
        return queueString;
    }

}
