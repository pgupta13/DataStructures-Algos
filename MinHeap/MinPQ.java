/**
 * Min priority queue API.
 */
public interface MinPQ extends Iterable<Comparable>
{
    /**
     * Gets the number of elements currently in the queue
     * @return size
     */
    public int size();
    
    /**
     * Determines if there are not elements in the queue.
     * @return true is no elements, false otherwise
     */
    public boolean isEmpty();
    
    /**
     * Inserts the key into the queue.
     * @param key
     */
    public void insert( Comparable key );
    
    /**
     * Finds and removes the key from the queue with the minimum value.
     * @return key
     * @throws java.util.NoSuchElementException if empty
     */
    public Comparable delMin( );

    /**
     * Finds the key from the queue with the minimum key value.
     * @return key
     * @throws java.util.NoSuchElementException if empty
     */
    public Comparable min();
}
