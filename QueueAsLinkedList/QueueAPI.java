package PA3;
/**
 * Queue API.
 * @author Zeehasham Rasheed
 * @param <Type>
 */
public interface QueueAPI <Type> extends Iterable<Type>
{
    /**
     * Returns number of items in the queue.
     * @return size
     */
    public int size();
    
    /**
     * Determines if queue is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty();
    
    /**
     * Adds an item to the end of the queue.
     * @param item 
     */
    public void enqueue( Type item );
    
    /**
     * Returns item at front of queue without removing.
     * @return item at front
     * @throws java.util.NoSuchElementException if empty
     */
    public Type peek();
    
    /**
     * Removes item from front of queue.
     * @return item removed from front
     * @throws java.util.NoSuchElementException if empty
     */
    public Type dequeue( );
}
