/**
 * SymbolTable stores key/value pairs where keys map to unique values.
 * Implementation that relies on keys with proper equals and
 * hashCode implementation.
 * <p>
 * Binary search is o.k. but two serious
 * limitations, need all items up front, then have to sort.  Maintaining
 * as new items occur is inefficient O(n).  Linked list is easy to
 * insert into O(1) but need to find first O(n).
 * <p>
 * Need way to add/insert items as they occur efficiently (ideally O(1)
 * but O(lg(n)) is good also).
 * @param <Key>
 * @param <Value>
 */
public interface BasicSymbolTable <Key, Value>
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
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value );

    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key );

    /**
     * Removes the Value for the given Key from the table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key );

    /**
     * Iterable that enumerates each key in the table.
     * @return iter
     */
    public Iterable<Key> keys();
}
